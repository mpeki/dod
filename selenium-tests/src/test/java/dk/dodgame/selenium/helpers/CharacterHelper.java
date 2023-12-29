package dk.dodgame.selenium.helpers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.dodgame.selenium.RandomStringGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CharacterHelper {

  private final WebDriver driver;

  public CharacterHelper(WebDriver webDriver) {
    this.driver = webDriver;
  }

  public void createCharacter(String name, Boolean hero, String ageGroup, String raceName, JavascriptExecutor js) {
    boolean isReady;
    do {
      isReady = js.executeScript("return document.readyState").equals("complete");
      NavigationHelper.wait(500);
    } while (!isReady);

    NavigationHelper.waitAndClick(driver, By.cssSelector(".MuiButtonBase-root:nth-child(2)"));
    {

      WebElement element = driver.findElement(
          NavigationHelper.waitFor(driver, By.cssSelector(".MuiFab-root > .MuiSvgIcon-root")));
      Actions builder = new Actions(driver);
      builder.moveToElement(element).perform();
    }
    NavigationHelper.waitAndClick(driver, By.cssSelector(".MuiFab-root > .MuiSvgIcon-root"));
    {
      WebElement element = driver.findElement(By.tagName("body"));
      Actions builder = new Actions(driver);
      builder.moveToElement(element, 0, 0).perform();
    }
    NavigationHelper.waitAndClick(driver, By.name("characterName"));
    NavigationHelper.findElement(driver, By.name("characterName")).sendKeys(name);
    if (hero) {
      NavigationHelper.waitAndClick(driver, By.name("hero"));
    }
    {
      WebElement dropdown = NavigationHelper.findElement(driver, By.name("ageGroup"));
      dropdown.findElement(By.xpath("//option[. = '" + ageGroup + "']")).click();
    }
    {
      WebElement dropdown = NavigationHelper.findElement(driver, By.name("raceName"));
      dropdown.findElement(By.xpath("//option[. = '" + raceName + "']")).click();
    }
    NavigationHelper.waitAndClick(driver, By.cssSelector(".AddCharacter_actions__eyw3q > button:nth-child(1)"));
  }

  public void deleteCharacter(String name) {
    WebElement namedCardSpan = NavigationHelper.findElement(driver, By.xpath("//span[text()='" + name + "']"));
    WebElement containerDiv = namedCardSpan.findElement(
        By.xpath(".//ancestor::div[contains(@class, 'MuiPaper-root')][1]"));
    WebElement deleteButton = containerDiv.findElement(By.xpath(".//button[@title='Delete']"));
    deleteButton.click();
    NavigationHelper.wait(500);
  }

  public void renameCharacter(String oldName, String newName) {
    selectCharacter(oldName);
    WebElement nameField = driver.findElement(By.xpath("//span[text()='Name']"));
    nameField.click();
    WebElement nameInputField = driver.switchTo().activeElement();
    nameInputField.sendKeys(newName);
    nameInputField.sendKeys(Keys.ENTER);
    NavigationHelper.gotoCharacters(driver);
    NavigationHelper.wait(300);
  }

  public void activateCharacter(String name) {
    WebElement namedCardSpan = NavigationHelper.findElement(driver, By.xpath("//span[text()='" + name + "']"));
    assertTrue(namedCardSpan.findElement(By.xpath("ancestor::a/div[2]")).getText().contains("Starting Silver"));
    namedCardSpan.findElement(By.xpath("//button[@title='Activate']")).click();
    NavigationHelper.wait(500);
    assertTrue(namedCardSpan.findElement(By.xpath("ancestor::a/div[2]")).getText().contains("Total Hit Points"));
  }

  public void buySkill(String skillCategory, String skillName, String fvValue) {
    WebElement addSkillButton = driver.findElement(By.xpath(
        "//div[span[text()='Base Skill Points']]/following-sibling::button[contains(@class, 'MuiButtonBase-root')]"));
    addSkillButton.click();
    WebElement filterDropdownInput = driver.findElement(
        By.xpath("//div[text()='Filter skills:']/following-sibling::div//input[@role='combobox']"));
    filterDropdownInput.click();
    filterDropdownInput.sendKeys(skillCategory);
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

  public void buySkills() {
    SkillInfo[] skillsToBuy = {
        new SkillInfo("combat", "primary.weapon", "15"),
        new SkillInfo("combat", "secondary.weapon", "15"),
        new SkillInfo("B", "Acrobatics", "5"),
        new SkillInfo("A", "fst", "15"),
        new SkillInfo("knowledge", "astrology", "10"),
        new SkillInfo("combat", "fast.draw", "10"),
        new SkillInfo("thieving", "sneak", "10"),
        new SkillInfo("communication", "bargain", "10"),
        new SkillInfo("thieving", "jump", "7"),
        new SkillInfo("thieving", "pick.locks", "6"),
        new SkillInfo("outdoor", "camouflage", "5"),
        new SkillInfo("outdoor", "animal.training", "5")};
    for (SkillInfo skill : skillsToBuy) {
      if (getRemainingSP() < 10) {
        break;
      }
      buySkill(skill.category, skill.skillName, skill.points);
    }
  }

  public void selectCharacter(String name) {
    WebElement namedCardSpan = driver.findElement(By.xpath("//span[text()='" + name + "']"));
    WebElement containerDiv = namedCardSpan.findElement(
        By.xpath(".//ancestor::div[contains(@class, 'MuiPaper-root')][1]"));
    containerDiv.click();
  }

  public String getRandomBo() {
    int random = (int) (Math.random() * 10) + 1;
    return "Bo " + RandomStringGenerator.getRandomString(random);
  }

  public int getRemainingSP() {
    NavigationHelper.wait(500);
    String remainingSP = driver.findElement(By.xpath("//div[span[text()='Base Skill Points']]/p")).getText();
    return Integer.parseInt(remainingSP);
  }

  public int getCurrentSilver() {
    NavigationHelper.wait(500);
    String silver = driver.findElement(By.xpath("//th[contains(text(), 'Silver')]/following-sibling::th")).getText();
    return Integer.parseInt(silver);
  }

  public void deleteSkill(String skillName) {
    driver.findElement(By.xpath("//td[contains(text(), '" + skillName + "')]/following-sibling::td[3]")).click();
  }

  public void buyWeapon(String weaponKey) {
    WebElement buyWeaponButton = driver.findElement(By.xpath(".//button[@title='Buy Weapon/Shield']"));
    buyWeaponButton.click();
    WebElement filterDropdownInput = driver.findElement(NavigationHelper.waitFor(driver,
        By.xpath("//label[text()='Select a Weapon']/following-sibling::div//input[@role='combobox']")));
    filterDropdownInput.click();
    filterDropdownInput.sendKeys(weaponKey);
    filterDropdownInput.sendKeys(Keys.ENTER);
    driver.findElement(By.xpath("//button[text()='Buy']")).click();
  }

  public void sellWeapon(String weaponKey) {
    NavigationHelper.waitAndClick(
        driver, By.xpath("//td[contains(text(), '" + weaponKey + "')]/following-sibling::td[8]"));
  }
}