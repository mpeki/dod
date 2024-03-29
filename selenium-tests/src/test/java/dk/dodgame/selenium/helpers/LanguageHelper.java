package dk.dodgame.selenium.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LanguageHelper {

  public static final String ENGLISH = "en";
  public static final String DANISH = "da";
  private final NavigationHelper navigationHelper;

  public LanguageHelper(WebDriver driver) {
    this.navigationHelper = new NavigationHelper(driver);
  }

  public void setLanguage(WebDriver driver, String language) {
    navigationHelper.waitFor(By.xpath("//button[@id='rfs-btn']"));
    driver.findElement(By.xpath("//button[@id='rfs-btn']")).click();
    if(language.equalsIgnoreCase("en") || language.equalsIgnoreCase("gb")){
      driver.findElement(By.xpath("//li[@id='rfs-GB']")).click();
    } else if(language.equalsIgnoreCase("da") || language.equalsIgnoreCase("dk")){
      driver.findElement(By.xpath("//li[@id='rfs-DK']")).click();
    } else {
      throw new RuntimeException("Language not supported");
    }
  }
}
