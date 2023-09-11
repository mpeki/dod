package dk.pekilidi.dodgame.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {


  @Test
  public void eightComponents() {
    WebDriver driver = new ChromeDriver();
    driver.get("http://ui:8081");

    String title = driver.getTitle();
    assertEquals("DoD :::...", title);

    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

//    WebElement textBox = driver.findElement(By.name("my-text"));
//    WebElement submitButton = driver.findElement(By.cssSelector("button"));
//
//    textBox.sendKeys("Selenium");
//    submitButton.click();
//
//    WebElement message = driver.findElement(By.id("message"));
//    String value = message.getText();
//    assertEquals("Received!", value);

    driver.quit();
  }
}
