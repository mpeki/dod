package dk.pekilidi.dodgame.selenium;

import static java.lang.Thread.sleep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper {

  public static void goHome(WebDriver driver) {
    driver.findElement(By.xpath("//a[contains(text(),'Home')]")).click();
  }

  public static void gotoCharacters(WebDriver driver) {
    driver.findElement(By.xpath("//a[contains(text(),'My Characters')]")).click();
  }


  public static void wait(int millis) {
    try {
      sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
