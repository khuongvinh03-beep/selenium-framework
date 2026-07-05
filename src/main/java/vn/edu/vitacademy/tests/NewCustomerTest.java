package vn.edu.vitacademy.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.vitacademy.pages.LoginPage;
import vn.edu.vitacademy.pages.NewCustomerPage;

public class NewCustomerTest extends BaseTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(NewCustomerTest.class);
  private LoginPage loginPage;
  private NewCustomerPage newCustomerPage;

  @BeforeClass(alwaysRun = true)
  public void loginAndNavigateToNewCustomer() {
    this.loginPage = loginPage();
    this.newCustomerPage = newCustomerPage();
    loginPage.loginWithValidAccount(baseUrl);
    loginPage.leftMenu().moveToNewCustomer();
  }

  @DataProvider(name = "customerNameValidationData")
  public Object[][] getCustomerNameValidationData() {
    return new Object[][] {
        { "", "Customer name must not be blank" },
        { "1234", "Numbers are not allowed" },
        { "Name!@#", "Special characters are not allowed" },
        { " name", "First character can not have space" }
    };
  }

  @Test(dataProvider = "customerNameValidationData", description = "Kiểm tra validate trường Customer Name")
  public void TC_verify_customer_name_validation(String inputName, String expectedErrorMessage) {
    LOGGER.info("Bắt đầu kiểm tra validate với giá trị đầu vào: '{}'", inputName);
    newCustomerPage.inputCustomerName(inputName);
    newCustomerPage.pressTabOnCustomerName();
    String actualErrorMessage = newCustomerPage.getCustomerNameValidationError();
    LOGGER.info("Thông báo lỗi thực tế hiển thị: '{}' (Mong đợi: '{}')", actualErrorMessage, expectedErrorMessage);
    Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
        "Lỗi: hông báo validate trường Customer Name không khớp!");
  }

  @DataProvider(name = "customerAddressValidationData")
  public Object[][] getCustomerAddressValidationData() {
    return new Object[][] { { " ", "First character can not have space" } };
  }

  @Test(dataProvider = "customerAddressValidationData", description = "Kiểm tra validate trường Customer Address")
  public void TC_verify_customer_Address_validation(String inputAddress, String expectedErrorMessage) {
    LOGGER.info("Bắt đầu kiểm tra validate với giá trị đầu vào: '{}'", inputAddress);
    newCustomerPage.inputCustomerAddress(inputAddress);
    newCustomerPage.pressTabOnCustomerAddress();
    String actualErrorMessage = newCustomerPage.getCustomerAddressValidationError();
    LOGGER.info("Thông báo lỗi thực tế hiển thị: '{}' (Mong đợi: '{}')", actualErrorMessage, expectedErrorMessage);
    Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
        "Lỗi: Thông báo validate trường Customer Name không khớp!");
  }

  @DataProvider(name = "cityValidationData")
  public Object[][] getCityValidationData() {
    return new Object[][] {
        { "", "City Field must not be blank" },
        { "1234", "Numbers are not allowed" },
        { "city123", "Numbers are not allowed" },
        { "City!@#", "Special characters are not allowed" },
        { "!@#", "Special characters are not allowed" },
        { " city", "First character can not have space" }
    };
  }

  @Test(dataProvider = "cityValidationData", description = "Kiểm tra validate trường City")
  public void TC_verify_customer_city_validation(String inputCity, String expectedErrorMessage) {
    newCustomerPage.inputCustomerCity(inputCity);
    newCustomerPage.pressTabOnCustomerCity();

    String actualErrorMessage = newCustomerPage.getCustomerCityValidationError();

    Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
        "Lỗi: Thông báo validate trường City không khớp!");
  }

  @DataProvider(name = "stateValidationData")
  public Object[][] getStateValidationData() {
    return new Object[][] {
        { "", "State must not be blank" },
        { "1234", "Numbers are not allowed" },
        { "State123", "Numbers are not allowed" },
        { "State!@#", "Special characters are not allowed" },
        { "!@#", "Special characters are not allowed" },
        { " state", "First character can not have space" }
    };
  }

  @Test(dataProvider = "stateValidationData", description = "Kiểm tra validate trường State")
  public void TC_verify_customer_state_validation(String inputState, String expectedErrorMessage) {
    newCustomerPage.inputCustomerState(inputState);
    newCustomerPage.pressTabOnCustomerState();

    String actualErrorMessage = newCustomerPage.getCustomerStateValidationError();

    Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
        "Lỗi: Thông báo validate trường State không khớp!");
  }

  @DataProvider(name = "pinValidationData")
  public Object[][] getPinValidationData() {
    return new Object[][] {
        { "PIN123", "Characters are not allowed" },
        { "1234PIN", "Characters are not allowed" },
        { "", "PIN Code must not be blank" },
        { "12", "PIN Code must have 6 Digits" },
        { "123", "PIN Code must have 6 Digits" },
        { "!@#", "Special characters are not allowed" },
        { "123!@#", "Special characters are not allowed" },
        { " 123456", "First character can not have space" },
        { "123 123", "Characters are not allowed" }
    };
  }

  @Test(dataProvider = "pinValidationData", description = "Kiểm tra validate trường PIN")
  public void TC_verify_customer_pin_validation(String inputPin, String expectedErrorMessage) {
    newCustomerPage.inputCustomerPin(inputPin);
    newCustomerPage.pressTabOnCustomerPin();

    String actualErrorMessage = newCustomerPage.getCustomerPinValidationError();

    Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
        "Lỗi: Thông báo validate trường PIN không khớp!");
  }

  @DataProvider(name = "mobileValidationData")
  public Object[][] getMobileValidationData() {
    return new Object[][] {
        { "", "Mobile no must not be blank" },
        { " 123456789", "First character can not have space" },
        { "123 123", "Characters are not allowed" },
        { "886636!@12", "Special characters are not allowed" },
        { "!@88662682", "Special characters are not allowed" },
        { "88663682!@", "Special characters are not allowed" }
    };
  }

  @Test(dataProvider = "mobileValidationData", description = "Kiểm tra validate trường Mobile")
  public void TC_verify_customer_mobile_validation(String inputMobile, String expectedErrorMessage) {
    newCustomerPage.inputCustomerMobile(inputMobile);
    newCustomerPage.pressTabOnCustomerMobile();

    String actualErrorMessage = newCustomerPage.getCustomerMobileValidationError();

    Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
        "Lỗi: Thông báo validate trường Mobile không khớp!");
  }

  @DataProvider(name = "emailValidationData")
  public Object[][] getEmailValidationData() {
    return new Object[][] {
        { "", "Email-ID must not be blank" },
        { "guru99@gmail", "Email-ID is not valid" },
        { "guru99", "Email-ID is not valid" },
        { "Guru99@", "Email-ID is not valid" },
        { "guru99@gmail.", "Email-ID is not valid" },
        { "guru99gmail.com", "Email-ID is not valid" },
        { "guru 99@gmail.com", "Email-ID is not valid" }
    };
  }

  @Test(dataProvider = "emailValidationData", description = "Kiểm tra validate trường Email")
  public void TC_verify_customer_email_validation(String inputEmail, String expectedErrorMessage) {
    newCustomerPage.inputCustomerEmail(inputEmail);
    newCustomerPage.pressTabOnCustomerEmail();

    String actualErrorMessage = newCustomerPage.getCustomerEmailValidationError();

    Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
        "Lỗi: Thông báo validate trường Email không khớp!");
  }

  @DataProvider(name = "positiveTestCases")
  public Object[][] getPositiveTestCases() {
    return new Object[][] {
        {
            "Valid Data",
            "male",
            "09-09-1998",
            "Luufsdfsfsf",
            "456 Le Duan District 1",
            "Ho Chi Minh",
            "SG",
            "812345",
            "0901234567",
            "khuongvgdfg@gmail.com",
            "12345"
        },
        {
            "With All Caps",
            "female",
            "09-09-1998",
            "LONGfsdfsdf",
            "123 ABC Street",
            "NEW YORK",
            "NY",
            "543210",
            "1234567890",
            "khuongvigdfg@gmail.com",
            "12345"
        }
    };
  }

  @Test(dataProvider = "positiveTestCases", description = "Kiểm tra tạo khách hàng mới với dữ liệu hợp lệ")
  public void TC_NewCustomer_ValidData(String testCaseName, String gender, String dob, String customerName,
      String address, String city, String state, String pinCode, String mobileNo, String email, String password) {

    loginPage.leftMenu().moveToNewCustomer();

    newCustomerPage.createCustomer(customerName, gender, dob, address, city, state,
        pinCode, mobileNo, email, password);

    String successMessage = newCustomerPage.getSuccessMessage();
    LOGGER.info("Message: {}", successMessage);

    Assert.assertTrue(successMessage.contains("Customer Registered Successfully"),
        "Lỗi: Không hiển thị thông báo thành công!");

  }
}
