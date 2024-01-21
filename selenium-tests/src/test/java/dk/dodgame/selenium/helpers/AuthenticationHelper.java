package dk.dodgame.selenium.helpers;

import dk.dodgame.selenium.RandomStringGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AuthenticationHelper {

  private final WebDriver driver;
  private final NavigationHelper navigationHelper;

  public AuthenticationHelper(WebDriver driver) {
    this.driver = driver;
    this.navigationHelper = new NavigationHelper(driver);
  }

  public static class TestUser {
    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
  }

  //Create a random user
  public TestUser createRandomTestUser(){
    TestUser testUser = new TestUser();
    testUser.firstName = RandomStringGenerator.getRandomString(5);
    testUser.lastName = RandomStringGenerator.getRandomString(5);
    testUser.email = testUser.firstName + "@" + testUser.lastName + ".dk";
    testUser.username = testUser.firstName + testUser.lastName;
    testUser.password = testUser.firstName + testUser.lastName + "123";
    return testUser;
  }

  public TestUser createRandomUser(){
    navigationHelper.wait(300);
    {

      WebElement element = driver.findElement(
          navigationHelper.waitFor(By.cssSelector(".MuiTab-root > .MuiSvgIcon-root"))
      );
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
    navigationHelper.waitAndClick(By.linkText("Register"));
    TestUser testUser = createRandomTestUser();
    driver.findElement(By.id("firstName")).sendKeys(testUser.firstName);
    driver.findElement(By.id("lastName")).sendKeys(testUser.lastName);
    driver.findElement(By.id("email")).sendKeys(testUser.email);
    driver.findElement(By.id("username")).sendKeys(testUser.username);
    driver.findElement(By.id("password")).sendKeys(testUser.password);
    driver.findElement(By.id("password-confirm")).sendKeys(testUser.password);
    driver.findElement(By.id("password-confirm")).sendKeys(Keys.ENTER);
    return testUser;
  }

  public void login(WebDriver driver, String username, String password){
    navigationHelper.wait(300);
    {

      WebElement element = driver.findElement(
          navigationHelper.waitFor(By.cssSelector(".MuiTab-root > .MuiSvgIcon-root"))
      );
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
    navigationHelper.waitFor(By.id("username"));
    driver.findElement(By.id("username")).sendKeys("msp");
    driver.findElement(By.id("password")).sendKeys("msp123");
    driver.findElement(By.id("password")).sendKeys(Keys.ENTER);

  }

  public static void logout(WebDriver driver){
    driver.findElement(By.cssSelector(".css-nt05bc")).click();
    driver.findElement(By.id("kc-logout")).click();
  }

}
