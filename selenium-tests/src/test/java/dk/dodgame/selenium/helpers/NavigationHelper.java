package dk.dodgame.selenium.helpers;

import static java.lang.Thread.sleep;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavigationHelper {

  public static final String HOME_TAB = "Home";
  public static final String CHARACTERS_TAB = "Characters";

  public static void goHome(WebDriver driver) {
    driver.findElement(NavigationHelper.waitFor(driver, By.xpath("//a[contains(text(), '" + HOME_TAB + "')]"))).click();
  }

  public static void gotoCharacters(WebDriver driver) {
    try {
      driver
          .findElement(NavigationHelper.waitFor(driver, By.xpath("//a[contains(text(), '" + CHARACTERS_TAB + "')]")))
          .click();
    } catch (ElementClickInterceptedException e) {
      Actions action = new Actions(driver);
      action.sendKeys("2").perform();
    }
  }

  public static void wait(int millis) {
    try {
      sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static WebElement findElement(WebDriver driver, By locator) {
    return driver.findElement(waitFor(driver, locator));
  }

  public static By waitFor(WebDriver driver, By locator) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(1));
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    wait.until(ExpectedConditions.elementToBeClickable(locator));
    return locator;
  }

  public static void waitAndClick(WebDriver driver, By locator) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(1));
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    wait.until(ExpectedConditions.elementToBeClickable(locator));
    WebElement element = driver.findElement(locator);
    try {
      element.click();
    } catch (ElementClickInterceptedException e) {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].click();", element);
    }
  }
}
