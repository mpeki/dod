package dk.dodgame.selenium.helpers;

import static java.lang.Thread.sleep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NavigationHelper {

  public static final String HOME_TAB = "Home";
  public static final String CHARACTERS_TAB = "Characters";

  public static void goHome(WebDriver driver) {
    driver.findElement(
      NavigationHelper.waitFor(driver, By.xpath("//a[contains(text(), '"+HOME_TAB+"')]"))
      ).click();
  }

  public static void gotoCharacters(WebDriver driver) {
    driver.findElement(
      NavigationHelper.waitFor(driver, By.xpath("//a[contains(text(), '"+CHARACTERS_TAB+"')]"))
    ).click();
  }

  public static void wait(int millis) {
    try {
      sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static By waitFor(WebDriver driver, By locator){
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    wait.until(ExpectedConditions.elementToBeClickable(locator));
    return locator;
  }

}
