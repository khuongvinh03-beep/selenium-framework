package Guru99.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Guru99.common.keywords.WebUI;
import Guru99.common.helper.JsonHelper;
import Guru99.model.UserCredential;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LoginPage extends BasePage {
  private final JsonHelper locators = new JsonHelper("LoginPage.json");

  public void registerAccount(String email) {
    webUI.inputText(locators.getLocator("TXT_EMAIL_ID"), email);
    webUI.clickOn(locators.getLocator("BTN_SUBMIT"));
  }

  public String getUserID() {
    return webUI.getText(locators.getLocator("LBL_USER_ID"));
  }

  public String getPassword() {
    return webUI.getText(locators.getLocator("LBL_PASSWORD"));
  }

  public LoginPage(WebUI webUI) {
    super(webUI);
  }

  public void clickRegisterHereLink() {
    webUI.clickOn(locators.getLocator("BTN_REGISTER_HERE"));
  }

  public void accountRegister(String email) {
    webUI.inputText(locators.getLocator("TXT_EMAIL_ID"), email);
  }

  public boolean isRegisterPageDisplayed() {
    return webUI.waitForElementVisible(locators.getLocator("TXT_EMAIL_ID"), 15);
  }

  public void getInformation(String username, String password) {
    webUI.inputText(locators.getLocator("TXT_USERNAME"), username);
    webUI.inputText(locators.getLocator("TXT_PASSWORD"), password);
    webUI.clickOn(locators.getLocator("BTN_LOGIN"));
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);
  private static final String CREDENTIALS_FILE = "src/main/resources/object_repository/user_credentials.json";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /**
   * Phương thức đăng nhập thông minh:
   * - Tải thông tin tài khoản đã lưu trong file user_credentials.json lên.
   * - Kiểm tra xem tài khoản có hợp lệ (chưa quá 20 ngày) không.
   * - Nếu tài khoản hợp lệ: Đăng nhập trực tiếp luôn.
   * - Nếu tài khoản đã hết hạn hoặc chưa có: Thực hiện đăng ký mới, lưu lại thông tin đăng nhập mới vào file JSON, sau đó mới đăng nhập.
   *
   * @param baseUrl URL trang chủ của ngân hàng Guru99 (ví dụ: https://demo.guru99.com/V4/index.php)
   */
  public void loginWithValidAccount(String baseUrl) {
    // 1. Đọc thông tin từ file cấu hình JSON
    UserCredential credentials = JsonHelper.readJsonFile(CREDENTIALS_FILE, UserCredential.class);

    // 2. Kiểm tra nếu tài khoản đã lưu vẫn còn hạn sử dụng (dưới 20 ngày)
    if (isCredentialsValid(credentials)) {
      LOGGER.info("Tái sử dụng tài khoản còn hạn: Username={}", credentials.getUsername());
    } else {
      // 3. Nếu tài khoản đã quá hạn (hoặc chưa từng đăng ký), đăng ký mới
      credentials = registerAndSaveNewAccount(baseUrl);
    }

    // 4. Thực hiện điền tài khoản, mật khẩu và click Đăng nhập
    getInformation(credentials.getUsername(), credentials.getPassword());
  }

  /**
   * Kiểm tra thông tin tài khoản đã lưu có hợp lệ (dưới 20 ngày) hay không.
   *
   * @param credentials Đối tượng thông tin tài khoản cần kiểm tra
   * @return true nếu tài khoản còn hạn dưới 20 ngày, ngược lại trả về false
   */
  private boolean isCredentialsValid(UserCredential credentials) {
    if (credentials == null || credentials.getRegisterDate() == null) {
      return false; // Chưa có thông tin -> Không hợp lệ
    }
    try {
      // Phân tích cú pháp ngày đăng ký từ chuỗi String sang đối tượng LocalDate
      LocalDate registerDate = LocalDate.parse(credentials.getRegisterDate(), DATE_FORMATTER);
      LocalDate currentDate = LocalDate.now();
      
      // Tính toán khoảng cách chênh lệch số ngày giữa ngày đăng ký và ngày hiện tại
      long daysElapsed = ChronoUnit.DAYS.between(registerDate, currentDate);
      LOGGER.info("Tài khoản đã tạo: {} ngày (Ngày tạo: {}).", daysElapsed, registerDate);
      
      // Kiểm tra nếu tài khoản được đăng ký chưa quá 20 ngày
      return daysElapsed < 20;
    } catch (Exception e) {
      LOGGER.warn("Không thể xác định ngày đăng ký cũ. Lỗi: {}", e.getMessage());
      return false; // Có lỗi xảy ra -> Coi như không hợp lệ và cần đăng ký mới
    }
  }

  /**
   * Thực hiện quy trình đăng ký tài khoản tự động và lưu thông tin đăng nhập mới.
   *
   * @param baseUrl URL trang chủ để quay lại đăng nhập
   * @return Đối tượng chứa tài khoản và mật khẩu vừa đăng ký mới thành công
   */
  private UserCredential registerAndSaveNewAccount(String baseUrl) {
    LOGGER.info("Tài khoản chưa có hoặc đã quá 20 ngày. Đang đăng ký tài khoản mới...");
    clickRegisterHereLink(); // Click link "here" để bắt đầu đăng ký

    // Lấy thông tin cửa sổ hiện tại (original handle)
    String originalWindow = webUI.getDriver().getWindowHandle();
    
    // Lấy danh sách tất cả các cửa sổ/tab đang mở
    java.util.Set<String> windowHandles = webUI.getDriver().getWindowHandles();
    boolean isNewWindowOpened = windowHandles.size() > 1;

    // Nếu trình duyệt mở link đăng ký trong tab mới, chuyển đổi focus của driver sang tab mới đó
    if (isNewWindowOpened) {
      for (String windowHandle : windowHandles) {
        if (!originalWindow.contentEquals(windowHandle)) {
          webUI.getDriver().switchTo().window(windowHandle);
          break;
        }
      }
    }

    // Đợi trang đăng ký hiển thị thành công (chờ ô nhập email hiển thị)
    if (!isRegisterPageDisplayed()) {
      throw new RuntimeException("Lỗi: Không chuyển hướng sang trang đăng ký thành công!");
    }

    // Tạo email ngẫu nhiên theo thời gian hiện tại để tránh trùng lặp
    String dynamicEmail = "vitacademy_" + System.currentTimeMillis() + "@gmail.com";
    registerAccount(dynamicEmail); // Điền email và click Submit đăng ký

    // Lấy thông tin Username & Password được hệ thống tự động sinh ra trên màn hình
    String generatedUsername = getUserID();
    String generatedPassword = getPassword();
    LOGGER.info("Đăng ký tài khoản động thành công - Username: {}, Password: {}", generatedUsername, generatedPassword);

    // Tạo đối tượng thông tin đăng nhập và lưu lại vào file JSON
    String currentDateStr = LocalDate.now().format(DATE_FORMATTER);
    UserCredential newCreds = new UserCredential(generatedUsername, generatedPassword, currentDateStr);
    JsonHelper.writeJsonFile(CREDENTIALS_FILE, newCreds);

    // Nếu trước đó đã chuyển đổi sang tab mới, tiến hành đóng tab mới đó và quay lại tab ban đầu
    if (isNewWindowOpened) {
      webUI.getDriver().close(); // Đóng tab đăng ký
      webUI.getDriver().switchTo().window(originalWindow); // Quay về tab gốc
    }

    // Quay lại trang chủ của ngân hàng để chuẩn bị đăng nhập
    webUI.navigateToUrl(baseUrl);

    return newCreds;
  }
}