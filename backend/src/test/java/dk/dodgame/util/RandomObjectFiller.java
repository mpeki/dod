package dk.dodgame.util;

import static org.reflections.scanners.Scanners.SubTypes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

@Slf4j
public class RandomObjectFiller {

  private final Random random = new Random();

  @SuppressWarnings("unchecked")
  public <T> T createAndFill(Class<T> clazz) {
    if (Modifier.isAbstract(clazz.getModifiers())) {
      Reflections reflections = new Reflections("dk.dodgame.domain.character");
      Set<Class<?>> subTypes = reflections.get(SubTypes.of(clazz).asClass());
      clazz = subTypes.stream().findAny().isPresent() ? (Class<T>) subTypes.stream().findAny().get().asSubclass(clazz)
          : null;
      return createAndFill(subTypes.stream().findAny().get().asSubclass(clazz));
    }
    T instance = null;
    try {
      instance = clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      log.info("Could not create instance of class: " + clazz.getName());
      return null;
    }
    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      Object value = null;
      if (field.getType().equals(Class.class)) {
        continue;
      }
      if (field.getType().equals(List.class) || field.getType().equals(Map.class)) {
        value = getRandomValueForField(clazz, field);
      } else {
        value = getRandomValueForField(field);
      }
      if (!field.getName().equals("serialVersionUID")) {
        try {
          field.set(instance, value);
        } catch (IllegalAccessException e) {
          log.info("Could not set value for field: " + field.getName() + " in class: " + clazz.getName());
        }
      }
    }
    return instance;
  }

  private <T> Object getRandomValueForField(Class<T> clazz, Field field) {
    Class<?> type = field.getType();
    if (type.equals(List.class)) {
      Field f = null;
      try {
        f = clazz.getDeclaredField(field.getName());
      } catch (NoSuchFieldException e) {
        log.info("Could not find field: " + field.getName() + " in class: " + clazz.getName());
      }
      assert f != null;
      ParameterizedType stringListType = (ParameterizedType) f.getGenericType();
      Class<?> listClass = (Class<?>) stringListType.getActualTypeArguments()[0];
      try {
        if(listClass.isEnum()){
          return Collections.emptyList();
        }
        return List.of(listClass.getDeclaredConstructor().newInstance());
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new RuntimeException(e);
      }
    } else if (type.equals(Map.class)) {
      return Collections.emptyMap();
    }
    return createAndFill(type);
  }

  private Object getRandomValueForField(Field field) {
    Class<?> type = field.getType();

    // Note that we must handle the different types here! This is just an
    // example, so this list is not complete! Adapt this to your needs!
    if (type.isEnum()) {
      Object[] enumValues = type.getEnumConstants();
      return enumValues[random.nextInt(enumValues.length)];
    } else if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
      return random.nextBoolean();
    } else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
      return random.nextInt();
    } else if (type.equals(Long.TYPE) || type.equals(Long.class)) {
      return random.nextLong();
    } else if (type.equals(Double.TYPE) || type.equals(Double.class)) {
      return random.nextDouble();
    } else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
      return random.nextFloat();
    } else if (type.equals(String.class)) {
      return UUID.randomUUID().toString();
    } else if (type.equals(BigInteger.class)) {
      return BigInteger.valueOf(random.nextInt());
    }
    return createAndFill(type);
  }
}
