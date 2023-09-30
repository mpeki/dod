package dk.pekilidi.dodgame.selenium.helpers;

import static java.lang.Thread.sleep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper {

  public static final String HOME_TAB = "Home";
  public static final String CHARACTERS_TAB = "Characters";

  public static void goHome(WebDriver driver) {
    driver.findElement(By.xpath("//a[contains(text(), '"+HOME_TAB+"')]")).click();
  }

  public static void gotoCharacters(WebDriver driver) {
    driver.findElement(By.xpath("//a[contains(text(), '"+CHARACTERS_TAB+"')]")).click();
  }

  public static void wait(int millis) {
    try {
      sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
