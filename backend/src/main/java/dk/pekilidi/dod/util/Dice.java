package dk.pekilidi.dod.util;

import java.security.SecureRandom;

public class Dice {

  private Dice() {
  }

  public static int roll(String dieSpec) {
    int result = 0;
    String[] dieSpecArr = dieSpec.toLowerCase().split("[t+]", 3);
    for (int i = 0; i < Integer.parseInt(dieSpecArr[0]); i++) {
      result += roll(Integer.parseInt(dieSpecArr[1]));
    }
    if (dieSpecArr.length == 3) {
      result += Integer.parseInt(dieSpecArr[2]);
    }
    return result;
  }

  private static int roll(int eyes) {
    return new SecureRandom().nextInt(eyes) + 1;
  }
}
