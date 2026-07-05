package Guru99.common.keywords;

import io.qameta.allure.Attachment;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class WebUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUI.class);
    private static final int DEFAULT_TIMEOUT = 30;
    // TODO: Khai báo biến toàn cục driver kiểu WebDriver
    private WebDriver driver;

    public void openBrowser(String browserName, String... url) {
        // 1. Kiểm tra browserName: nếu là "Chrome", khởi tạo driver = new
        // ChromeDriver();
        // 2. Nếu browserName là "Firefox", khởi tạo driver = new FirefoxDriver();
        // 3. Nếu url có truyền vào (url.length > 0 và không rỗng), gọi
        // driver.get(url[0]);
        try {
            String targetUrl = (url.length > 0) ? url[0] : "";
            LOGGER.info("Opening browser: {} with URL: {}", browserName.toLowerCase(), targetUrl);
            switch (browserName.toLowerCase()) {
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "chrome":
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    driver = new ChromeDriver(options);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }
            if (!targetUrl.isEmpty()) {
                driver.get(targetUrl);
            }
            LOGGER.info("Browser {} open successfully with URL: {}", browserName.toLowerCase(), targetUrl);
        } catch (Exception e) {
            LOGGER.error("Fail to open browser: {}  root cause: {}", browserName.toLowerCase(), e.getMessage());
        }
    }

    public void closeBrowser() {
        // 1. Kiểm tra nếu driver khác null
        // 2. Gọi driver.quit(); để đóng toàn bộ trình duyệt và giải phóng bộ nhớ
        try {
            LOGGER.info("Closing browser");
            driver.quit();
            LOGGER.info("Browser closed successfully");
        } catch (Exception e) {
            LOGGER.error("Fail to close browser. Root Cause {}", e.getMessage());
        }
    }

    public void maximizeWindow() {
        // 1. Gọi driver.manage().window().maximize(); để phóng to màn hình trình duyệt
        try {
            LOGGER.info("Maximizing browser window");
            driver.manage().window().maximize();
            LOGGER.info("Browser window maximized successfully");
        } catch (Exception e) {
            LOGGER.error("Fail to maximize browser window. Root Cause {}", e.getMessage());
        }
    }

    private By findBy(String locator) {
        String prefix = StringUtils.substringBefore(locator, ":");
        String locatorValue = StringUtils.substringAfter(locator, ":");
        switch (prefix.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "css":
                return By.cssSelector(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "class":
                return By.className(locatorValue);
            case "link_text":
                return By.linkText(locatorValue);
            case "partial_link_text":
                return By.partialLinkText(locatorValue);
            case "tag":
                return By.tagName(locatorValue);
            default:
                return By.xpath(locator);
        }
    }

    public WebElement findWebElement(String locator, int... timeout) {
        // 1. Phân tích chuỗi locator (ví dụ: dùng hàm helper findBy để kiểm tra prefix
        // id:, css:, xpath:)
        // 2. Sử dụng WebDriverWait để chờ phần tử presenceOfElementLocated(By)
        // 3. Trả về đối tượng WebElement tìm được, nếu lỗi hoặc quá hạn timeout thì log
        // lỗi và trả về null
        try {
            int waitTime = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
            LOGGER.info("Waiting for web element: {} (timeout: {}s)", locator, waitTime);
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            WebElement we = wait.until(ExpectedConditions.presenceOfElementLocated(findBy(locator)));
            LOGGER.info("Web element {} found", locator);
            if (we != null) {
                LOGGER.info("Found 1 web element located by '{}'", locator);
                return we;
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find web element: {} root cause: {}", locator, e.getMessage());
        }
        return null;
    }

    public List<WebElement> findWebElements(String locator, int... timeout) {
        // 1. Phân tích chuỗi locator
        // 2. Sử dụng WebDriverWait chờ presenceOfAllElementsLocatedBy(By)
        // 3. Trả về danh sách List<WebElement> tìm được
        int waitTime = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
        try {
            LOGGER.info("Waiting for web elements: {} (timeout: {}s)", locator, waitTime);
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            List<WebElement> weList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(findBy(locator)));
            LOGGER.info("Web elements {} found", locator);
            if (weList != null) {
                LOGGER.info("Found {} web elements located by '{}'", weList.size(), locator);
                return weList;
            }
        } catch (Exception e) {
            LOGGER.error("Fail to find web elements: {} root cause: {}", locator, e.getMessage());
        }
        return null;
    }

    public void inputText(String locator, String text, int... timeout) {
        // 1. Gọi hàm findWebElement(locator, timeout) để tìm phần tử
        // 2. Gọi hàm element.sendKeys(text) để nhập văn bản vào trường nhập liệu
        WebElement we = findWebElement(locator, timeout);
        try {
            LOGGER.info("Typing text in element: {}", locator);
            we.sendKeys(text);
            LOGGER.info("Input text '{}' to element '{}' successfully", text, locator);
            sleep(1); // Thêm độ trễ 1 giây để người dùng dễ theo dõi kịch bản
        } catch (Exception e) {
            LOGGER.error("Fail to input text: {} root cause: {}", text, e.getMessage());
        }
    }

    public void pressTab(String locator) {
        try {
            LOGGER.info("Pressing TAB key on element: {}", locator);
            WebElement we = findWebElement(locator);
            we.sendKeys(Keys.TAB);
            LOGGER.info("TAB key pressed successfully");
            sleep(1); // Thêm độ trễ 1 giây để người dùng dễ theo dõi kịch bản
        } catch (Exception e) {
            LOGGER.error("Fail to press TAB key on element: {} root cause: {}", locator, e.getMessage());
        }
    }

    public void clickOn(String locator, int... timeout) {
        // 1. Gọi hàm findWebElement(locator, timeout) để tìm phần tử
        // 2. Gọi hàm element.click() để thực hiện nhấp chuột
        WebElement we = findWebElement(locator, timeout);
        try {
            LOGGER.info("Click on web element {} found", locator);
            we.click();
            LOGGER.info("Click on web element {} found successfully", locator);
            sleep(1); // Thêm độ trễ 1 giây để người dùng dễ theo dõi kịch bản
        } catch (Exception e) {
            LOGGER.error("Fail to click on web element: {} root cause: {}", locator, e.getMessage());
        }
    }

    public void clickOn(WebElement we) {
        // 1. Nhấp chuột trực tiếp vào đối tượng WebElement truyền vào: we.click()
        try {
            LOGGER.info("Click on web element {} found", we);
            we.click();
            LOGGER.info("Click on web element {} found successfully", we);
            sleep(1); // Thêm độ trễ 1 giây để người dùng dễ theo dõi kịch bản
        } catch (Exception e) {
            LOGGER.error("Fail to click on web element");
        }
    }

    public void clearText(String locator, int... timeout) {
        // 1. Gọi hàm findWebElement(locator, timeout) để tìm phần tử
        // 2. Gọi hàm element.clear() để xóa sạch ký tự hiện có trong ô nhập
        WebElement element = findWebElement(locator, timeout);
        try {
            LOGGER.info("Clear text {} found", locator);
            element.clear();
            LOGGER.info("Clear text {} found successfully", locator);
            sleep(1); // Thêm độ trễ 1 giây để người dùng dễ theo dõi kịch bản
        } catch (Exception e) {
            LOGGER.error("Fail to clear text: {} root cause: {}", locator, e.getMessage());
        }
    }

    public String getText(String locator, int... timeout) {
        // 1. Gọi hàm findWebElement(locator, timeout) để tìm phần tử
        // 2. Gọi và trả về kết quả của hàm element.getText() để lấy nội dung text
        WebElement we = findWebElement(locator, timeout);
        try {
            LOGGER.info("Getting text from element: {}", locator);
            String text = we.getText();
            LOGGER.info("Got text '{}' from element '{}' successfully", text, locator);
            return text;
        } catch (Exception e) {
            LOGGER.error("Fail to get text: {} root cause: {}", locator, e.getMessage());
        }
        return null;
    }

    public boolean waitForElementVisible(String locator, int timeout) {
        // 1. Khởi tạo WebDriverWait với thời gian chờ là timeout (giây)
        // 2. Gọi
        // wait.until(ExpectedConditions.visibilityOfElementLocated(findBy(locator)))
        // 3. Trả về true nếu phần tử hiển thị, ngược lại bắt ngoại lệ và trả về false
        try {
            LOGGER.info("Waiting for web element located by '{}' to be visible within {} second(s)", locator, timeout);
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement we = wait.until(ExpectedConditions.visibilityOfElementLocated(findBy(locator)));
            if (we != null) {
                LOGGER.info("Web element located by '{}' is visible", locator);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("Fail to wait for web element: {} root cause: {}", locator, e.getMessage());
        }
        return false;
    }

    public boolean waitForElementPresent(String locator, int timeout) {
        // 1. Khởi tạo WebDriverWait với thời gian chờ là timeout (giây)
        // 2. Gọi
        // wait.until(ExpectedConditions.presenceOfElementLocated(findBy(locator)))
        // 3. Trả về true nếu phần tử xuất hiện trong DOM, ngược lại bắt ngoại lệ và trả
        // về false
        try {
            LOGGER.info("Waiting for web element located by '{}' to be present within {} second(s)", locator, timeout);
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            WebElement we = wait.until(ExpectedConditions.presenceOfElementLocated(findBy(locator)));
            if (we != null) {
                LOGGER.info("Web element located by '{}' is present", locator);
                return true;
            }
            LOGGER.error("Web element located by '{}' is not present within {} second(s)", locator, timeout);
        } catch (Exception e) {
            LOGGER.error("Failed to wait for web element located by '{}' to be present. Root cause: {}", locator,
                    e.getMessage());
        }
        return false;

    }

    public boolean waitForAlert(int... timeout) {
        // 1. Khởi tạo WebDriverWait với thời gian chờ là timeout (giây)
        // 2. Gọi wait.until(ExpectedConditions.alertIsPresent())
        // 3. Trả về true nếu có alert, bắt ngoại lệ và trả về false nếu không xuất hiện
        // alert
        int waitTime = timeout.length > 0 ? timeout[0] : DEFAULT_TIMEOUT;
        try {
            LOGGER.info("Waiting for alert within {} second(s)", waitTime);
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            LOGGER.info("Alert is present");
            if (alert != null) {
                LOGGER.info("Alert is present");
                return true;
            }
            LOGGER.error("Alert is not present");
        } catch (Exception e) {
            LOGGER.error("Fail to wait for alert. Root cause: {}", e.getMessage());
        }
        return false;
    }

    public String getAlertText() {
        // 1. Chuyển hướng sang alert: Alert alert = driver.switchTo().alert();
        // 2. Trả về nội dung văn bản của alert bằng alert.getText();
        try {
            LOGGER.info("Get alert text within {} second(s)", DEFAULT_TIMEOUT);
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            LOGGER.info("Get alert text successfully");
            return alertText;
        } catch (Exception e) {
            LOGGER.error("Fail to get alert text. Root cause: {}", e.getMessage());
        }
        return null;
    }

    public void acceptAlert() {
        // 1. Chuyển hướng sang alert: Alert alert = driver.switchTo().alert();
        // 2. Đồng ý alert bằng cách gọi alert.accept();
        try {
            LOGGER.info("Accepting alert");
            Alert alert = driver.switchTo().alert();
            alert.accept();
            LOGGER.info("Alert accepted");
        } catch (Exception e) {
            LOGGER.error("Failed to accept alert. Root cause: {}", e.getMessage());
        }
    }

    public void dismissAlert() {
        // 1. Chuyển hướng sang alert: Alert alert = driver.switchTo().alert();
        // 2. Bỏ qua alert bằng cách gọi alert.dismiss();
        try {
            LOGGER.info("Dismissing alert");
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            LOGGER.info("Alert dismissed");
        } catch (Exception e) {
            LOGGER.error("Failed to dismiss alert. Root cause: {}", e.getMessage());
        }
    }

    public void sleep(double seconds) {
        try {
            Thread.sleep((long) (seconds * 1000));
            LOGGER.info("Slept for {} seconds", seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Sleep interrupted: {}", e.getMessage());
        }
    }

    public String getCurrentUrl() {
        try {
            String url = driver.getCurrentUrl();
            LOGGER.info("Current URL: {}", url);
            return url;
        } catch (Exception e) {
            LOGGER.error("Fail to get current URL. Root cause: {}", e.getMessage());
            return "";
        }
    }

    public String getPageTitle() {
        try {
            String title = driver.getTitle();
            LOGGER.info("Page Title: {}", title);
            return title;
        } catch (Exception e) {
            LOGGER.error("Fail to get page title. Root cause: {}", e.getMessage());
            return "";
        }
    }

    public void navigateToUrl(String url) {
        try {
            LOGGER.info("Navigating to URL: {}", url);
            driver.get(url);
            LOGGER.info("Navigated to URL successfully");
        } catch (Exception e) {
            LOGGER.error("Fail to navigate to URL: {} root cause: {}", url, e.getMessage());
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachmentScreenshot() {
        // 1. Ép kiểu driver sang TakesScreenshot: ((TakesScreenshot) driver)
        // 2. Gọi getScreenshotAs(OutputType.BYTES) và trả về mảng byte[] để đính kèm
        // Allure Report
        try {
            LOGGER.info("Taking screenshot");
            byte[] images = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            if (images != null) {
                LOGGER.info("Taking screenshot successfully");
                return images;
            }
        } catch (Exception e) {
            LOGGER.error("Fail to take screenshot. Root cause: {}", e.getMessage());
        }
        return null;
    }

    public void delayInSeconds(int seconds) {
        try {
            LOGGER.info("Delaying for {} seconds", seconds);
            Thread.sleep(seconds * 1000L);
            LOGGER.info("Delay completed");
        } catch (InterruptedException e) {
            LOGGER.error("Delay interrupted. Root cause: {}", e.getMessage());
        }
    }

    public WebDriver getDriver() {
        return this.driver;
    }
}