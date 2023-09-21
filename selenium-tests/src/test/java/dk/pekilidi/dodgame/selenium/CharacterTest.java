package dk.pekilidi.dodgame.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

@ExtendWith(ScreenshotOnFailureExtension.class)
@Tag("integration")
class CharacterTest {

  static JavascriptExecutor js;
  private static WebDriver driver;
  private static Map<String, Object> vars;

  @BeforeAll
  static void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");

    ChromeDriverService service = new ChromeDriverService.Builder().withLogOutput(System.out).build();

    driver = new ChromeDriver(service, options);

    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
    js = (JavascriptExecutor) driver;

    vars = new HashMap<String, Object>();

    driver.get("http://ui:8081/");
    driver.manage().window().setSize(new Dimension(1265, 1420));
    ScreenshotOnFailureExtension.setDriver(driver);

    AuthenticationHelper.login(driver, "msp", "msp123");
  }

  @AfterAll
  static void tearDown() {
    AuthenticationHelper.logout(driver);
    driver.quit();
  }

  @BeforeEach
  void beforeEach() {
    NavigationHelper.goHome(driver);
  }

  @Test
  void createAndDeleteCharacter() {
    String charName = CharacterHelper.getRandomBo();
    NavigationHelper.gotoCharacters(driver);
    CharacterHelper.createCharacter(driver, charName, true, "Old", "human");
    WebElement nameSpan = driver.findElement(By.xpath("//span[text()='" + charName + "']"));
    assertEquals(charName, nameSpan.getText());
    CharacterHelper.deleteCharacter(driver, charName);
    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + charName + "']")));
  }

  @Test
  void createRenameAndDeleteCharacter() {
    String oldBo = CharacterHelper.getRandomBo();
    String newBo = CharacterHelper.getRandomBo();
    NavigationHelper.gotoCharacters(driver);
    CharacterHelper.createCharacter(driver, oldBo, true, "Old", "human");

    assertEquals(oldBo, driver.findElement(By.xpath("//span[text()='" + oldBo + "']")).getText());
    CharacterHelper.renameCharacter(driver, oldBo, newBo);
    assertEquals(newBo, driver.findElement(By.xpath("//span[text()='" + newBo + "']")).getText());

    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + oldBo + "']")));

    CharacterHelper.deleteCharacter(driver, newBo);

    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + newBo + "']")));
  }

  @Test
  void createCharacterAndBuySkills() {
    String bo = CharacterHelper.getRandomBo();
    CharacterHelper.createCharacter(driver, bo, true, "Young", "human");
    assertEquals(bo, driver.findElement(By.xpath("//span[text()='" + bo + "']")).getText());
    CharacterHelper.selectCharacter(driver, bo);
    assertTrue(CharacterHelper.getRemainingSP(driver) == 260);
    CharacterHelper.buySkill(driver, "combat","primary.weapon", "15");
    CharacterHelper.buySkill(driver, "combat","secondary.weapon", "15");
    CharacterHelper.buySkill(driver, "B","Acrobatics", "3");
    CharacterHelper.buySkill(driver, "A","fst", "15");
    CharacterHelper.buySkill(driver, "knowledge","astrology", "10");
    CharacterHelper.buySkill(driver, "combat","fast.draw", "10");
    CharacterHelper.buySkill(driver, "thieving","sneak", "10");
    CharacterHelper.buySkill(driver, "communication","bargain", "10");
    int remainingSP = CharacterHelper.getRemainingSP(driver);
    assertTrue(remainingSP  < 260);
    if(remainingSP > 10){
      CharacterHelper.buySkill(driver, "thieving","jump", "5");
    }
    if(CharacterHelper.getRemainingSP(driver) > 10){
      CharacterHelper.buySkill(driver, "thieving","pick.locks", "5");
    }
    NavigationHelper.gotoCharacters(driver);
    CharacterHelper.deleteCharacter(driver, bo);
    NavigationHelper.goHome(driver);

    assertThrows(
        org.openqa.selenium.NoSuchElementException.class,
        () -> driver.findElement(By.xpath("//span[text()='" + bo + "']")));
  }
}
