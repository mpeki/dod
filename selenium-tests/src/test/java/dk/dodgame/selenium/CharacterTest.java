package dk.dodgame.selenium;

import static dk.dodgame.selenium.helpers.AuthenticationHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.dodgame.selenium.helpers.AuthenticationHelper;
import dk.dodgame.selenium.helpers.CharacterHelper;
import dk.dodgame.selenium.helpers.LanguageHelper;
import dk.dodgame.selenium.helpers.NavigationHelper;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

@ExtendWith(ScreenshotOnFailureExtension.class)
@Tag("integration")
class CharacterTest {

  static JavascriptExecutor js;
  private static WebDriver driver;
  private static Map<String, Object> vars;
  private CharacterHelper characterHelper;

  @BeforeAll
  static void setUp() throws MalformedURLException, URISyntaxException {
    ChromeOptions options = new ChromeOptions();
    //    options.addArguments("--headless");
    options.addArguments("--window-size=1920,1080");

    URL hubUrl = new URI("http://localhost:4444/wd/hub").toURL();
    driver = new RemoteWebDriver(hubUrl, options);

    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
    js = (JavascriptExecutor) driver;

    vars = new HashMap<String, Object>();

    driver.get("http://ui:80/");
    driver.manage().window().maximize();
    ScreenshotOnFailureExtension.setDriver(driver);
    TestUser testUser = createRandomUser(driver);
//    AuthenticationHelper.login(driver, "msp", "msp123");
    LanguageHelper.setLanguage(driver, LanguageHelper.ENGLISH);
  }

  @AfterAll
  static void tearDown() {
    logout(driver);
    driver.quit();
  }

  @BeforeEach
  void beforeEach() {
    characterHelper = new CharacterHelper(driver);
    NavigationHelper.goHome(driver);
  }

  @Test
  void createAndDeleteCharacter() {
    String charName = characterHelper.getRandomBo();
    NavigationHelper.gotoCharacters(driver);
    characterHelper.createCharacter(charName, true, "OLD", "human", js);
    WebElement nameSpan = NavigationHelper.findElement(driver, By.xpath("//span[text()='" + charName + "']"));
    assertEquals(charName, nameSpan.getText());
    characterHelper.deleteCharacter(charName);
    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + charName + "']")));
  }

  @Test
  void createRenameAndDeleteCharacter() {
    String oldBo = characterHelper.getRandomBo();
    String newBo = characterHelper.getRandomBo();
    NavigationHelper.gotoCharacters(driver);
    characterHelper.createCharacter(oldBo, true, "OLD", "human", js);

    assertEquals(oldBo, NavigationHelper.findElement(driver, By.xpath("//span[text()='" + oldBo + "']")).getText());
    characterHelper.renameCharacter(oldBo, newBo);
    assertEquals(newBo, NavigationHelper.findElement(driver, By.xpath("//span[text()='" + newBo + "']")).getText());

    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + oldBo + "']")));

    characterHelper.deleteCharacter(newBo);

    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + newBo + "']")));
  }

  @Test
  void createBuySkillRemoveSkillAndDeleteCharacter() {
    String bo = characterHelper.getRandomBo();
    NavigationHelper.gotoCharacters(driver);
    characterHelper.createCharacter(bo, true, "OLD", "human", js);

    assertEquals(bo, NavigationHelper.findElement(driver, By.xpath("//span[text()='" + bo + "']")).getText());

    characterHelper.selectCharacter(bo);

    int spBefore = characterHelper.getRemainingSP();
    characterHelper.buySkill("B", "Acrobatics", "5");
    int spAfter = characterHelper.getRemainingSP();
    assertTrue(spAfter < spBefore);
    characterHelper.deleteSkill("Acrobatics");
    spAfter = characterHelper.getRemainingSP();
    assertEquals(spBefore, spAfter);
    NavigationHelper.gotoCharacters(driver);
    characterHelper.deleteCharacter(bo);
    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + bo + "']")));
  }

  @Test
  void createCharacterAndBuySkillsAndActivate() {
    String bo = characterHelper.getRandomBo();
    characterHelper.createCharacter(bo, true, "YOUNG", "human", js);
    assertEquals(bo, driver.findElement(By.xpath("//span[text()='" + bo + "']")).getText());
    characterHelper.selectCharacter(bo);
    assertEquals(260, characterHelper.getRemainingSP());
    characterHelper.buySkills();
    int remainingSP = characterHelper.getRemainingSP();
    assertTrue(remainingSP < 10, "Remaining SP: " + remainingSP);
    NavigationHelper.gotoCharacters(driver);
    characterHelper.activateCharacter(bo);
    characterHelper.deleteCharacter(bo);

    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + bo + "']")));

    NavigationHelper.goHome(driver);
  }

  @Test
  void createBuyWeaponSellWeaponAndDeleteCharacter() {
    String bo = characterHelper.getRandomBo();
    NavigationHelper.gotoCharacters(driver);
    characterHelper.createCharacter(bo, true, "OLD", "human", js);
    assertEquals(bo, driver.findElement(By.xpath("//span[text()='" + bo + "']")).getText());

    characterHelper.selectCharacter(bo);
    int startingSilver = characterHelper.getCurrentSilver();
    characterHelper.buyWeapon("hammer");
    int currentSilver = characterHelper.getCurrentSilver();
    assertEquals(25, startingSilver - currentSilver);
    characterHelper.sellWeapon("Hammer");
    currentSilver = characterHelper.getCurrentSilver();
    assertEquals(startingSilver, currentSilver);
    NavigationHelper.gotoCharacters(driver);
    characterHelper.deleteCharacter(bo);

    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + bo + "']")));
  }
}