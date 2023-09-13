package dk.pekilidi.dod.util;

import java.security.SecureRandom;

public class Dice {

  public static final String PLAIN_REGEX = "[t]";
  public static final String ADD_REGEX = "[t+]";
  public static final String MULTI_REGEX = "[tx]";

  private Dice() {
  }

  public static int roll(String dieSpec) {
    int result;
    if(dieSpec.contains("+")) {
      result = doAdditionRoll(dieSpec);
    } else if(dieSpec.contains("x")){
      result = doMultiplication(dieSpec);
    } else {
      result = doDefault(dieSpec);
    }
    return result;
  }

  private static int doDefault(String dieSpec){
    int result = 0;
    String[] dieSpecArr = dieSpec.toLowerCase().split(PLAIN_REGEX, 2);
    for (int i = 0; i < Integer.parseInt(dieSpecArr[0]); i++) {
      result += roll(Integer.parseInt(dieSpecArr[1]));
    }
    return result;
  }

  private static int doMultiplication(String dieSpec) {
    int result = 0;
    String[] dieSpecArr = dieSpec.toLowerCase().split(MULTI_REGEX, 3);
    for (int i = 0; i < Integer.parseInt(dieSpecArr[0]); i++) {
      result += roll(Integer.parseInt(dieSpecArr[1]));
    }
    if (dieSpecArr.length == 3) {
      result = result * Integer.parseInt(dieSpecArr[2]);
    }
    return result;
  }

  private static int doAdditionRoll(String dieSpec) {
    int result = 0;
    String[] dieSpecArr = dieSpec.toLowerCase().split(ADD_REGEX, 3);
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

