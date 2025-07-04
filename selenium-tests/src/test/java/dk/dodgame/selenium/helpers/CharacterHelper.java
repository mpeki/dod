package dk.dodgame.selenium.helpers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

public class CharacterHelper {

	private final WebDriver driver;
	private final NavigationHelper navigationHelper;

	private Actions actions;
	private WebDriverWait wait;
	private Faker faker;


	public CharacterHelper(WebDriver webDriver) {
		this.driver = webDriver;
		this.navigationHelper = new NavigationHelper(driver);
		this.actions = new Actions(driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.faker = new Faker();
	}

	public void createCharacter(String name, Boolean hero, String ageGroup, String raceName, JavascriptExecutor js) {
		boolean isReady;
		do {
			isReady = js.executeScript("return document.readyState").equals("complete");
			navigationHelper.wait(500);
		} while (!isReady);

		navigationHelper.waitAndClick(By.cssSelector(".MuiButtonBase-root:nth-child(2)"));
		{

			WebElement element = driver.findElement(
					navigationHelper.waitFor(By.cssSelector(".MuiFab-root > .MuiSvgIcon-root")));
			Actions builder = new Actions(driver);
			builder.moveToElement(element).perform();
		}
		navigationHelper.waitAndClick(By.cssSelector(".MuiFab-root > .MuiSvgIcon-root"));
		{
			WebElement element = driver.findElement(By.tagName("body"));
			Actions builder = new Actions(driver);
			builder.moveToElement(element, 0, 0).perform();
		}
		navigationHelper.waitAndClick(By.name("characterName"));
		navigationHelper.findElement(By.name("characterName")).sendKeys(name);
		if (hero) {
			navigationHelper.waitAndClick(By.name("hero"));
		}
		selectAgeGroup(ageGroup);
		selectRace(raceName);
		navigationHelper.waitAndClick(By.xpath("//button[contains(@type, 'submit')]"));


	}

	public void deleteCharacter(String name) {
		WebElement namedCardSpan = navigationHelper.findElement(By.xpath("//span[text()='" + name + "']"));
		WebElement containerDiv = namedCardSpan.findElement(
				By.xpath(".//ancestor::div[contains(@class, 'MuiPaper-root')][1]"));
		WebElement deleteButton = containerDiv.findElement(By.xpath(".//button[@title='Delete']"));
		deleteButton.click();
		navigationHelper.wait(500);
	}

	public void renameCharacter(String oldName, String newName) {
		selectCharacter(oldName);
		WebElement nameField = driver.findElement(By.xpath("//span[text()='Name']"));
		nameField.click();
		WebElement nameInputField = driver.switchTo().activeElement();
		nameInputField.sendKeys(newName);
		nameInputField.sendKeys(Keys.ENTER);
		navigationHelper.gotoCharacters();
		navigationHelper.wait(300);
	}

	public void activateCharacter(String name) {
		WebElement namedCardSpan = navigationHelper.findElement(By.xpath("//span[text()='" + name + "']"));
		assertTrue(namedCardSpan.findElement(By.xpath("ancestor::a/div[2]")).getText().contains("Starting Silver"));
		namedCardSpan.findElement(By.xpath("//button[@title='Activate']")).click();
		navigationHelper.wait(500);
		assertTrue(namedCardSpan.findElement(By.xpath("ancestor::a/div[2]")).getText().contains("Total Hit Points"));
	}

	public void buySkill(String skillCategory, String skillName, String fvValue) {
		WebElement addSkillButton = navigationHelper.findElement(By.xpath(
				"//div[span[text()='Base Skill Points']]/following-sibling::button[contains(@class, 'MuiButtonBase-root')]"));
		addSkillButton.click();
		WebElement filterDropdownInput = driver.findElement(
				By.xpath("//div[text()='1. Set Filter']/following-sibling::div//input[@role='combobox']"));

		filterDropdownInput.click();
		filterDropdownInput.sendKeys(skillCategory);
		filterDropdownInput.sendKeys(Keys.ENTER);
		WebElement skillDropdownInput = driver.findElement(
				By.xpath("//div[text()='2. Choose Skill']/following-sibling::div//input[@role='combobox']"));
		skillDropdownInput.click();
		skillDropdownInput.sendKeys(skillName);
		skillDropdownInput.sendKeys(Keys.TAB);
		if ("primary.weapon".equalsIgnoreCase(skillName)) {
			skillDropdownInput.sendKeys(Keys.TAB);
			WebElement weaponDropdownInput = driver.switchTo().activeElement();
			weaponDropdownInput.sendKeys("short.sword");
			weaponDropdownInput.sendKeys(Keys.TAB);
		}

		if ("secondary.weapon".equalsIgnoreCase(skillName)) {
			skillDropdownInput.sendKeys(Keys.TAB);
			WebElement weaponDropdownInput = driver.switchTo().activeElement();
			weaponDropdownInput.sendKeys("short.sword");
			weaponDropdownInput.sendKeys(Keys.TAB);
		}

		WebElement fvToBuy = driver.findElement(By.xpath("//input[@name='modifier']"));
		fvToBuy.click();
		fvToBuy.sendKeys(fvValue);

		WebElement buyButton = driver.findElement(By.xpath("//button[text()='Buy']"));
		navigationHelper.wait(300);
		if (buyButton.isEnabled()) {
			navigationHelper.waitAndClick(By.xpath("//button[text()='Buy']"));
		} else {
			navigationHelper.waitAndClick(By.xpath("//button[text()='Cancel']"));
		}
		navigationHelper.wait(300);
	}

	public void buySkills() {
		SkillInfo[] skillsToBuy = {
				new SkillInfo("combat", "primary.weapon", "15"),
				new SkillInfo("combat", "secondary.weapon", "15"),
				new SkillInfo("B", "Acrobatics", "5"),
				new SkillInfo("A", "fst", "15"),
				new SkillInfo("knowledge", "astrology", "10"),
				new SkillInfo("combat", "fast.draw", "10"),
				new SkillInfo("thieving", "sneak", "8"),
				new SkillInfo("communication", "bargain", "8"),
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
		return faker.name().firstName();
	}

	public int getRemainingSP() {
		navigationHelper.wait(500);
		String remainingSP = driver.findElement(By.xpath("//div[span[text()='Base Skill Points']]/p")).getText();
		return Integer.parseInt(remainingSP);
	}

	public int getCurrentSilver() {
		navigationHelper.wait(500);
		String silver = driver.findElement(By.xpath("//th[contains(text(), 'Silver')]/following-sibling::th")).getText();
		return Integer.parseInt(silver);
	}

	public void deleteSkill(String skillName) {
		driver.findElement(By.xpath("//td[contains(text(), '" + skillName + "')]/following-sibling::td[3]")).click();
	}

	public void buyWeapon(String weaponKey) {
		WebElement buyWeaponButton = driver.findElement(By.xpath(".//button[@title='Buy Weapon/Shield']"));
		buyWeaponButton.click();
		actions.sendKeys(Keys.TAB, Keys.ARROW_DOWN, weaponKey, Keys.ENTER).perform();
		actions.sendKeys(Keys.TAB, Keys.TAB, "25", Keys.ENTER).perform();
		driver.findElement(By.xpath("//button[text()='Buy']")).click();
	}

	public void sellWeapon(String weaponKey) {
		navigationHelper.waitAndClick(By.xpath("//td[contains(text(), '" + weaponKey + "')]/following-sibling::td[8]"));
	}

	public void selectAgeGroup(String ageGroup) {
		WebElement ageDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[role='combobox']")));
		ageDropdown.click(); // Open the dropdown
		WebElement ageOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@role, 'option')][.//text()='" + ageGroup + "']")));
		ageOption.click();
	}

	public void selectRace(String race) {
		actions.sendKeys(Keys.TAB, Keys.ARROW_DOWN).perform();
		WebElement raceOption = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//ul[contains(@role, 'listbox')]//li[contains(text(), '" + race + "')]")));
		raceOption.click();
	}


}
