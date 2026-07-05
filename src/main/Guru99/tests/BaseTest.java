package Guru99.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import Guru99.common.helper.FileHelper;
import Guru99.common.keywords.WebUI;
import Guru99.pages.LoginPage;
import Guru99.pages.NewCustomerPage;
import Guru99.common.helper.JsonHelper;
import Guru99.model.Config;

public class BaseTest {

  protected WebUI webUI;
  protected String baseUrl;
  private LoginPage loginPage;
  private NewCustomerPage newCustomerPage;

  public LoginPage loginPage() {
    if (loginPage == null) {
      loginPage = new LoginPage(webUI);
    }
    return loginPage;
  }

  public NewCustomerPage newCustomerPage() {
    if (newCustomerPage == null) {
      newCustomerPage = new NewCustomerPage(webUI);
    }
    return newCustomerPage;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(WebUI.class);

  @Parameters({ "browser", "url" })
  @BeforeClass(alwaysRun = true)
  public void beforeClass(@Optional("") String browser, @Optional("") String url) {
    LOGGER.info("Running test class: {}", this.getClass().getSimpleName());
    webUI = new WebUI();

    Config config = JsonHelper.readConfig("config.json");

    String finalBrowser = (browser != null && !browser.trim().isEmpty()) ? browser : config.getBrowser();
    String finalUrl = (url != null && !url.trim().isEmpty()) ? url : config.getUrl();

    this.baseUrl = finalUrl;
    webUI.openBrowser(finalBrowser, finalUrl);
    webUI.maximizeWindow();
  }

  @BeforeSuite(alwaysRun = true)
  public void beforeSuite() {
    LOGGER.info("Cleaning up old Allure results...");
    FileHelper.deleteFolder("target/allure-results");
  }

  @AfterClass(alwaysRun = true)
  public void afterClass() {
    LOGGER.info("Ended test class: {}", this.getClass().getSimpleName());
    if (webUI != null) {
      webUI.sleep(3);
      webUI.closeBrowser();
    }
  }

  public WebUI getWebUI() {
    return this.webUI;
  }
}