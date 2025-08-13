package dk.dodgame.selenium;

import static dk.dodgame.selenium.helpers.AuthenticationHelper.logout;
import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.artsok.RepeatedIfExceptionsTest;

import dk.dodgame.selenium.helpers.AuthenticationHelper;
import dk.dodgame.selenium.helpers.CharacterHelper;
import dk.dodgame.selenium.helpers.LanguageHelper;
import dk.dodgame.selenium.helpers.NavigationHelper;

@ExtendWith(ScreenshotOnFailureExtension.class)
@Tag("integration")
class CharacterTest {

    static JavascriptExecutor js;
    private static WebDriver driver;
    private CharacterHelper characterHelper;
    private NavigationHelper navigationHelper;
    private LanguageHelper languageHelper;

    private static final String AGE_GROUP_OLD = "Old";
    private static final String AGE_GROUP_YOUNG = "Young";
    private static final String RACE_HUMAN = "Human";
    private static final String RACE_DWARF = "Dwarf";
    private static final int TEST_NUM_RETRY = 3;
    private static final int TEST_MIN_SUCCESS = 1;

    @BeforeAll
    static void setUp() throws MalformedURLException, URISyntaxException {

		// Check if tests should run locally or against Selenium Hub
		String runMode = System.getProperty("seleniumRunMode", "local"); // Default to "local" if not specified
		String headlessMode = System.getProperty("headlessMode", "true"); // Default to "true" if not specified

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");

        // Only add headless argument if headlessMode is "true"
        if ("true".equalsIgnoreCase(headlessMode)) {
            options.addArguments("--headless");
        }

        if ("hub".equalsIgnoreCase(runMode)) {
            URL hubUrl = new URI("http://localhost:4444/wd/hub").toURL();
            driver = new RemoteWebDriver(hubUrl, options);
        } else {
            driver = new ChromeDriver(options);
        }

        AuthenticationHelper authenticationHelper = new AuthenticationHelper(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        js = (JavascriptExecutor) driver;

        driver.get("http://ui:80/");

        // Maximize window when not in headless mode
        if (!"true".equalsIgnoreCase(headlessMode)) {
            driver.manage().window().maximize();
        }
        ScreenshotOnFailureExtension.setDriver(driver);
        authenticationHelper.createRandomUserAndLogin();

    }

    @AfterAll
    static void tearDown() {
        logout(driver);
        driver.quit();
    }

    @BeforeEach
    void beforeEach() {
        characterHelper = new CharacterHelper(driver);
        navigationHelper = new NavigationHelper(driver);
        languageHelper = new LanguageHelper(driver);
        navigationHelper.goHome();
        languageHelper.setLanguage(driver, LanguageHelper.ENGLISH);
    }

    //  @RepeatedTest(value = TEST_NUM_RETRY, failureThreshold = TEST_FAIL_THRESHOLD)
    @DisplayName("Create and delete character.")
    @RepeatedIfExceptionsTest(name = "Rerun failed test. {currentRepetition}/{totalRepetitions}", repeats = TEST_NUM_RETRY, minSuccess = TEST_MIN_SUCCESS)
    void createAndDeleteCharacter() {
        String charName = characterHelper.getRandomBo();
        navigationHelper.gotoCharacters();
        characterHelper.createCharacter(charName, true, AGE_GROUP_OLD, RACE_HUMAN, js);
        WebElement nameSpan = navigationHelper.findElement(By.xpath("//span[text()='" + charName + "']"));
        assertEquals(charName, nameSpan.getText());
        characterHelper.deleteCharacter(charName);
        assertThrows(
                org.openqa.selenium.NoSuchElementException.class,
                () -> driver.findElement(By.xpath("//span[text()='" + charName + "']")));
    }

    @DisplayName("Create, rename, and delete character.")
    @RepeatedIfExceptionsTest(name = "Rerun failed test. {currentRepetition}/{totalRepetitions}", repeats = TEST_NUM_RETRY, minSuccess = TEST_MIN_SUCCESS)
    void createRenameAndDeleteCharacter() {
        String oldBo = characterHelper.getRandomBo();
        String newBo = characterHelper.getRandomBo();
        navigationHelper.gotoCharacters();
        characterHelper.createCharacter(oldBo, true, AGE_GROUP_OLD, RACE_HUMAN, js);
        String matchString = oldBo.length() > 10 ? oldBo.substring(0, 10) : oldBo;
        assertEquals(oldBo, navigationHelper.findElement(By.xpath("//span[starts-with(text(), '" + matchString + "')]")).getText());
        characterHelper.renameCharacter(oldBo, newBo);
        matchString = newBo.length() > 10 ? newBo.substring(0, 10) : newBo;
        assertEquals(newBo, navigationHelper.findElement(By.xpath("//span[starts-with(text(), '" + matchString + "')]")).getText());

        assertThrows(
                org.openqa.selenium.NoSuchElementException.class,
                () -> driver.findElement(By.xpath("//span[text()='" + oldBo + "']")));

        characterHelper.deleteCharacter(newBo);

        assertThrows(
                org.openqa.selenium.NoSuchElementException.class,
                () -> driver.findElement(By.xpath("//span[text()='" + newBo + "']")));
    }

    @DisplayName("Create a character, buy and remove skill, delete the character.")
    @RepeatedIfExceptionsTest(name = "Rerun failed test. {currentRepetition}/{totalRepetitions}", repeats = TEST_NUM_RETRY, minSuccess = TEST_MIN_SUCCESS)
    void createBuySkillRemoveSkillAndDeleteCharacter() {
        String bo = characterHelper.getRandomBo();
        navigationHelper.gotoCharacters();
        characterHelper.createCharacter(bo, true, AGE_GROUP_OLD, RACE_HUMAN, js);
        String matchString = bo.length() > 10 ? bo.substring(0, 10) : bo;
        assertEquals(bo, navigationHelper.findElement(By.xpath("//span[text()='" + matchString + "']")).getText());

        characterHelper.selectCharacter(bo);

        int spBefore = characterHelper.getRemainingSP();
        characterHelper.buySkill("B", "Acrobatics", "5");
        int spAfter = characterHelper.getRemainingSP();
        assertTrue(spAfter < spBefore);
        characterHelper.deleteSkill("Acrobatics");
        spAfter = characterHelper.getRemainingSP();
        assertEquals(spBefore, spAfter);
        navigationHelper.gotoCharacters();
        characterHelper.deleteCharacter(bo);
        assertThrows(
                org.openqa.selenium.NoSuchElementException.class,
                () -> driver.findElement(By.xpath("//span[text()='" + matchString + "']")));
    }

    @DisplayName("Create, activate and delete a character,")
    @RepeatedIfExceptionsTest(name = "Rerun failed test. {currentRepetition}/{totalRepetitions}", repeats = TEST_NUM_RETRY, minSuccess = TEST_MIN_SUCCESS)
    void createCharacterAndBuySkillsAndActivate() {
        String bo = characterHelper.getRandomBo();
        characterHelper.createCharacter(bo, true, AGE_GROUP_YOUNG, RACE_HUMAN, js);
        String matchString = bo.length() > 10 ? bo.substring(0, 10) : bo;
        assertEquals(bo, driver.findElement(By.xpath("//span[text()='" + matchString + "']")).getText());
        characterHelper.selectCharacter(bo);
        assertEquals(260, characterHelper.getRemainingSP());
        characterHelper.buySkills();
        int remainingSP = characterHelper.getRemainingSP();
        assertTrue(remainingSP < 10, "Remaining SP: " + remainingSP);
        navigationHelper.gotoCharacters();
        characterHelper.activateCharacter(bo);
        characterHelper.deleteCharacter(bo);

        assertThrows(
                org.openqa.selenium.NoSuchElementException.class,
                () -> driver.findElement(By.xpath("//span[text()='" + matchString + "']")));

        navigationHelper.goHome();
    }

    @DisplayName("Create, buy weapon, and delete character")
    @RepeatedIfExceptionsTest(name = "Rerun failed test. {currentRepetition}/{totalRepetitions}", repeats = TEST_NUM_RETRY, minSuccess = TEST_MIN_SUCCESS)
    void createBuyWeaponSellWeaponAndDeleteCharacter() {
        String bo = characterHelper.getRandomBo();
        navigationHelper.gotoCharacters();

        characterHelper.createCharacter(bo, true, AGE_GROUP_OLD, RACE_HUMAN, js);
        navigationHelper.gotoCharacters();

        String matchString = bo.length() > 10 ? bo.substring(0, 10) : bo;
        assertEquals(bo, driver.findElement(By.xpath("//span[text()='" + matchString + "']")).getText());

        characterHelper.selectCharacter(bo);
        int startingSilver = characterHelper.getCurrentSilver();
        characterHelper.buyWeapon("hammer");
        int currentSilver = characterHelper.getCurrentSilver();
        assertEquals(25, startingSilver - currentSilver);
        characterHelper.sellWeapon("hammer");
        currentSilver = characterHelper.getCurrentSilver();
        assertEquals(startingSilver, currentSilver);
        navigationHelper.gotoCharacters();
        characterHelper.deleteCharacter(bo);

        assertThrows(
                org.openqa.selenium.NoSuchElementException.class,
                () -> driver.findElement(By.xpath("//span[text()='" + matchString + "']")));
    }
}
