package dk.pekilidi.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RandomObjectFiller {

  private final Random random = new Random();

  public <T> T createAndFill(Class<T> clazz) throws Exception {
    T instance = clazz.getDeclaredConstructor().newInstance();
    for(Field field: clazz.getDeclaredFields()) {
      field.setAccessible(true);
      Object value = null;
      if(field.getType().equals(List.class)){
        value = getRandomValueForField(clazz,field);
      } else {
        value = getRandomValueForField(field);
      }
      if(field.getName() != "serialVersionUID" ){
        field.set(instance, value);
      }
    }
    return instance;
  }

  private <T> Object getRandomValueForField(Class<T> clazz, Field field) throws Exception {
    Class<?> type = field.getType();
    if(type.equals(List.class)){
      Field f = clazz.getDeclaredField(field.getName());
      ParameterizedType stringListType = (ParameterizedType) f.getGenericType();
      Class<?> listClass = (Class<?>) stringListType.getActualTypeArguments()[0];
      return List.of(listClass.getDeclaredConstructor().newInstance());
    }
    return createAndFill(type);
  }

  private Object getRandomValueForField(Field field) throws Exception {
    Class<?> type = field.getType();

    // Note that we must handle the different types here! This is just an
    // example, so this list is not complete! Adapt this to your needs!
    if(type.isEnum()) {
      Object[] enumValues = type.getEnumConstants();
      return enumValues[random.nextInt(enumValues.length)];
    } else if(type.equals(Integer.TYPE) || type.equals(Integer.class)) {
      return random.nextInt();
    } else if(type.equals(Long.TYPE) || type.equals(Long.class)) {
      return random.nextLong();
    } else if(type.equals(Double.TYPE) || type.equals(Double.class)) {
      return random.nextDouble();
    } else if(type.equals(Float.TYPE) || type.equals(Float.class)) {
      return random.nextFloat();
    } else if(type.equals(String.class)) {
      return UUID.randomUUID().toString();
    } else if(type.equals(BigInteger.class)){
      return BigInteger.valueOf(random.nextInt());
    }
    return createAndFill(type);
  }
}
