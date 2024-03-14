package dk.dodgame.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class DiceTest {

  @Test
  void callPrivateConstructorsForCodeCoverage()
      throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    Class<?>[] classesToConstruct = {Dice.class};
    for (Class<?> clazz : classesToConstruct) {
      Constructor<?> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      Assertions.assertNotNull(constructor.newInstance());
    }
  }

  @Test
  void testDie6Rolled3times() {
    int result = Dice.roll("3t6");
    assertTrue(result >= 3 && result <= 18);
  }

  @Test
  void testDie6Rolled3timesPlus3() {
    int result = Dice.roll("3t6+3");
    assertTrue(result >= 6 && result <= 21);
  }

  @Test
  void testDie4Rolled3timesMultipliedBy150() {
    int result = Dice.roll("4t3x150");
    assertTrue(result >= 600 && result <= 1800);
  }

  @Test
  void testDie6Rolled3timesPlus3times1000() {
    for (int i = 0; i < 1000; i++) {
      int result = Dice.roll("3t6+3");
      assertTrue(result >= 6 && result <= 21, "result should be between 6 and 21, was: " + result);
    }
  }
}
