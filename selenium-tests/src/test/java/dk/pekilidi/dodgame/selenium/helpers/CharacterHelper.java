package dk.pekilidi.dodgame.selenium.helpers;

import dk.pekilidi.dodgame.selenium.RandomStringGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CharacterHelper {

  public static void createCharacter(WebDriver driver, String name, Boolean hero, String ageGroup, String raceName) {
    driver.findElement(By.cssSelector(".MuiButtonBase-root:nth-child(2)")).click();
    {
      WebElement element = driver.findElement(By.cssSelector(".MuiFab-root > .MuiSvgIcon-root"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    driver.findElement(By.cssSelector(".MuiFab-root > .MuiSvgIcon-root")).click();
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    driver.findElement(By.name("characterName")).click();
    driver.findElement(By.name("characterName")).sendKeys(name);
    if (hero) {
      driver.findElement(By.name("hero")).click();
    }
    {
      WebElement dropdown = driver.findElement(By.name("ageGroup"));
      dropdown.findElement(By.xpath("//option[. = '" + ageGroup + "']")).click();
    }
    {
      WebElement dropdown = driver.findElement(By.name("raceName"));
      dropdown.findElement(By.xpath("//option[. = '" + raceName + "']")).click();
    }
    driver.findElement(By.cssSelector(".AddCharacter_actions__eyw3q > button:nth-child(1)")).click();
  }

  public static void deleteCharacter(WebDriver driver, String name) {
    WebElement namedCardSpan = driver.findElement(By.xpath("//span[text()='" + name + "']"));
    WebElement containerDiv = namedCardSpan.findElement(
        By.xpath(".//ancestor::div[contains(@class, 'MuiPaper-root')][1]"));
    WebElement deleteButton = containerDiv.findElement(By.xpath(".//button[@title='delete']"));
    deleteButton.click();
    NavigationHelper.wait(500);
  }

  public static void renameCharacter(WebDriver driver, String oldName, String newName) {
    selectCharacter(driver, oldName);
    WebElement nameField = driver.findElement(By.xpath("//span[text()='Name']"));
    nameField.click();
    WebElement nameInputField = driver.switchTo().activeElement();
    nameInputField.sendKeys(newName);
    nameInputField.sendKeys(Keys.ENTER);
    NavigationHelper.gotoCharacters(driver);
    NavigationHelper.wait(300);
  }

  public static void buySkill(WebDriver driver, String skillCatagory, String skillName, String fvValue) {
    WebElement addSkillButton = driver.findElement(By.xpath(
        "//div[span[text()='Base Skill Points']]/following-sibling::button[contains(@class, 'MuiButtonBase-root')]"));
    addSkillButton.click();
    WebElement filterDropdownInput = driver.findElement(
        By.xpath("//div[text()='Filter skills:']/following-sibling::div//input[@role='combobox']"));
    filterDropdownInput.click();
    filterDropdownInput.sendKeys(skillCatagory);
    filterDropdownInput.sendKeys(Keys.ENTER);
    WebElement skillDropdownInput = driver.findElement(
        By.xpath("//div[text()='Select a skill:']/following-sibling::div//input[@role='combobox']"));
    skillDropdownInput.click();
    skillDropdownInput.sendKeys(skillName);
    skillDropdownInput.sendKeys(Keys.ENTER);
    if ("primary.weapon".equalsIgnoreCase(skillName)) {
      skillDropdownInput.sendKeys(Keys.TAB);
      WebElement weaponDropdownInput = driver.switchTo().activeElement();
      weaponDropdownInput.sendKeys("short.sword");
      weaponDropdownInput.sendKeys(Keys.ENTER);
    }

    if ("secondary.weapon".equalsIgnoreCase(skillName)) {
      skillDropdownInput.sendKeys(Keys.TAB);
      WebElement weaponDropdownInput = driver.switchTo().activeElement();
      weaponDropdownInput.sendKeys("short.sword");
      weaponDropdownInput.sendKeys(Keys.ENTER);
    }

    WebElement fvToBuy = driver.findElement(By.xpath("//label[text()='FV to buy:']/following-sibling::input"));
    fvToBuy.click();
    fvToBuy.sendKeys(fvValue);
    driver.findElement(By.xpath("//button[text()='Buy']")).click();
    NavigationHelper.wait(300);
  }

  public static void selectCharacter(WebDriver driver, String name) {
    WebElement namedCardSpan = driver.findElement(By.xpath("//span[text()='" + name + "']"));
    WebElement containerDiv = namedCardSpan.findElement(
        By.xpath(".//ancestor::div[contains(@class, 'MuiPaper-root')][1]"));
    containerDiv.click();
  }

  public static String getRandomBo() {
    int random = (int) (Math.random() * 10) + 1;
    return "Bo " + RandomStringGenerator.getRandomString(random);
  }

  public static int getRemainingSP(WebDriver driver) {
    String remainingSP = driver.findElement(By.xpath("//div[span[text()='Base Skill Points']]/p")).getText();
    return Integer.parseInt(remainingSP);
  }
}
