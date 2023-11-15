package dk.dodgame.selenium.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AuthenticationHelper {

  public static void login(WebDriver driver, String username, String password){

    {
      WebElement element = driver.findElement(By.cssSelector(".MuiTab-root > .MuiSvgIcon-root"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    // 4 | click | css=.MuiTab-root > .MuiSvgIcon-root |
    driver.findElement(By.cssSelector(".MuiTab-root > .MuiSvgIcon-root")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.id("username")).sendKeys("msp");
    driver.findElement(By.id("password")).sendKeys("msp123");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);

  }

  public static void logout(WebDriver driver){
    driver.findElement(By.cssSelector(".css-nt05bc")).click();
    driver.findElement(By.id("kc-logout")).click();
  }

}
