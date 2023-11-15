package dk.dodgame.selenium;

import java.util.Random;

public class RandomStringGenerator {

  private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private static final Random RANDOM = new Random();

  public static String getRandomString(int length) {
    StringBuilder builder = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      builder.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
    }

    return builder.toString();
  }
}