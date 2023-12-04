package dk.dodgame.selenium;

import java.io.File;
import java.util.Date;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

public class ScreenshotOnFailureExtension implements AfterEachCallback {


  private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

  public static void setDriver(WebDriver driver) {
    driverThreadLocal.set(driver);
  }


  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    if (context.getExecutionException().isPresent()) {
      WebDriver driver = driverThreadLocal.get();
      TakesScreenshot ts = (TakesScreenshot) driver;
      File source = ts.getScreenshotAs(OutputType.FILE);
      String fileName = context.getDisplayName() + "-" + new Date().getTime() + ".png";
      File destination = new File("/tmp/selenium-screenshots/" + fileName);
      FileUtils.copyFile(source, destination);
    }
  }
}
