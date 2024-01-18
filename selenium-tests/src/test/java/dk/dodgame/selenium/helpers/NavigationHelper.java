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

  private final WebDriver driver;
  public static final String HOME_TAB = "Home";
  public static final String CHARACTERS_TAB = "Characters";

  public NavigationHelper(WebDriver driver) {
    this.driver = driver;
  }

  public void goHome() {
    driver.findElement(waitFor(By.xpath("//a[contains(text(), '" + HOME_TAB + "')]"))).click();
  }

  public void gotoCharacters() {
    try {
      driver
          .findElement(waitFor(By.xpath("//a[contains(text(), '" + CHARACTERS_TAB + "')]")))
          .click();
    } catch (ElementClickInterceptedException e) {
      Actions action = new Actions(driver);
      action.sendKeys("2").perform();
    }
  }

  public void wait(int millis) {
    try {
      sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public WebElement findElement(By locator) {
    return driver.findElement(waitFor(locator));
  }

  public By waitFor(By locator) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(1));
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    wait.until(ExpectedConditions.elementToBeClickable(locator));
    return locator;
  }

  public void waitAndClick(By locator) {
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
