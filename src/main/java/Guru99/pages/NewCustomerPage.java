package vn.edu.vitacademy.pages;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.common.helper.JsonHelper;

public class NewCustomerPage extends BasePage {

    private final JsonHelper locators = new JsonHelper("NewCustomerPage.json");

    protected final String TXT_CUSTOMER_NAME = locators.getLocator("TXT_CUSTOMER_NAME");
    protected final String RAD_GENDER_MALE = locators.getLocator("RAD_GENDER_MALE");
    protected final String RAD_GENDER_FEMALE = locators.getLocator("RAD_GENDER_FEMALE");
    protected final String TXT_DOB = locators.getLocator("TXT_DOB");
    protected final String TXT_ADDRESS = locators.getLocator("TXT_ADDRESS");
    protected final String TXT_CITY = locators.getLocator("TXT_CITY");
    protected final String TXT_STATE = locators.getLocator("TXT_STATE");
    protected final String TXT_PIN = locators.getLocator("TXT_PIN");
    protected final String TXT_MOBILE = locators.getLocator("TXT_MOBILE");
    protected final String TXT_EMAIL = locators.getLocator("TXT_EMAIL");
    protected final String TXT_PASSWORD = locators.getLocator("TXT_PASSWORD");
    protected final String BTN_SUBMIT = locators.getLocator("BTN_SUBMIT");
    protected final String BTN_RESET = locators.getLocator("BTN_RESET");

    // các error
    protected final String LBL_NAME_ERROR = locators.getLocator("LBL_NAME_ERROR");
    protected final String LBL_ADDRESS_ERROR = locators.getLocator("LBL_ADDRESS_ERROR");
    protected final String LBL_CITY_ERROR = locators.getLocator("LBL_CITY_ERROR");
    protected final String LBL_STATE_ERROR = locators.getLocator("LBL_STATE_ERROR");
    protected final String LBL_PIN_ERROR = locators.getLocator("LBL_PIN_ERROR");
    protected final String LBL_MOBILE_ERROR = locators.getLocator("LBL_MOBILE_ERROR");
    protected final String LBL_EMAIL_ERROR = locators.getLocator("LBL_EMAIL_ERROR");
    protected final String LBL_PASSWORD_ERROR = locators.getLocator("LBL_PASSWORD_ERROR");
    protected final String LBL_MOBILE_LABEL = locators.getLocator("LBL_MOBILE_LABEL");
    protected final String LBL_SUCCESS_MSG = locators.getLocator("LBL_SUCCESS_MSG");

    public NewCustomerPage(WebUI webUI) {
        super(webUI);
    }

    // nhập dữ liệu
    public void inputCustomerName(String name) {
        webUI.clearText(TXT_CUSTOMER_NAME);
        webUI.inputText(TXT_CUSTOMER_NAME, name);
    }

    public void inputCustomerAddress(String address) {
        webUI.clearText(TXT_ADDRESS);
        webUI.inputText(TXT_ADDRESS, address);
    }

    public void inputCustomerCity(String city) {
        webUI.clearText(TXT_CITY);
        webUI.inputText(TXT_CITY, city);
    }

    public void inputCustomerState(String state) {
        webUI.clearText(TXT_STATE);
        webUI.inputText(TXT_STATE, state);
    }

    public void inputCustomerPin(String pin) {
        webUI.clearText(TXT_PIN);
        webUI.inputText(TXT_PIN, pin);
    }

    public void inputCustomerMobile(String mobile) {
        webUI.clearText(TXT_MOBILE);
        webUI.inputText(TXT_MOBILE, mobile);
    }

    public void inputCustomerEmail(String email) {
        webUI.clearText(TXT_EMAIL);
        webUI.inputText(TXT_EMAIL, email);
    }

    public void inputCustomerPassword(String password) {
        webUI.clearText(TXT_PASSWORD);
        webUI.inputText(TXT_PASSWORD, password);
    }

    // create
    public void createCustomer(String name, String gender, String dob, String address, String city, String state,
            String pinCode, String mobileNo, String email, String password) {
        webUI.inputText(TXT_CUSTOMER_NAME, name);

        if ("male".equalsIgnoreCase(gender) || "m".equalsIgnoreCase(gender)) {
            webUI.clickOn(RAD_GENDER_MALE);
        } else {
            webUI.clickOn(RAD_GENDER_FEMALE);
        }
        webUI.inputText(TXT_DOB, dob);
        webUI.inputText(TXT_ADDRESS, address);
        webUI.inputText(TXT_CITY, city);
        webUI.inputText(TXT_STATE, state);
        webUI.inputText(TXT_PIN, pinCode);
        webUI.inputText(TXT_MOBILE, mobileNo);
        webUI.inputText(TXT_EMAIL, email);
        webUI.inputText(TXT_PASSWORD, password);
        webUI.clickOn(BTN_SUBMIT);
    }

    // lấy validate lỗi
    public String getCustomerNameValidationError() {
        webUI.waitForElementVisible(LBL_NAME_ERROR, 5);
        return webUI.getText(LBL_NAME_ERROR);
    }

    public String getCustomerAddressValidationError() {
        webUI.waitForElementVisible(LBL_ADDRESS_ERROR, 5);
        return webUI.getText(LBL_ADDRESS_ERROR);
    }

    public String getCustomerCityValidationError() {
        webUI.waitForElementVisible(LBL_CITY_ERROR, 5);
        return webUI.getText(LBL_CITY_ERROR);
    }

    public String getCustomerStateValidationError() {
        webUI.waitForElementVisible(LBL_STATE_ERROR, 5);
        return webUI.getText(LBL_STATE_ERROR);
    }

    public String getCustomerPinValidationError() {
        webUI.waitForElementVisible(LBL_PIN_ERROR, 5);
        return webUI.getText(LBL_PIN_ERROR);
    }

    public String getCustomerMobileValidationError() {
        webUI.waitForElementVisible(LBL_MOBILE_ERROR, 5);
        return webUI.getText(LBL_MOBILE_ERROR);
    }

    public String getCustomerEmailValidationError() {
        webUI.waitForElementVisible(LBL_EMAIL_ERROR, 5);
        return webUI.getText(LBL_EMAIL_ERROR);
    }

    public String getCustomerPasswordValidationError() {
        webUI.waitForElementVisible(LBL_PASSWORD_ERROR, 5);
        return webUI.getText(LBL_PASSWORD_ERROR);
    }

    public String getTelephoneLabel() {
        return webUI.getText(LBL_MOBILE_LABEL);
    }

    // thực hiện tab
    public void pressTabOnCustomerName() {
        webUI.pressTab(TXT_CUSTOMER_NAME);
    }

    public void pressTabOnCustomerAddress() {
        webUI.pressTab(TXT_ADDRESS);
    }

    public void pressTabOnCustomerCity() {
        webUI.pressTab(TXT_CITY);
    }

    public void pressTabOnCustomerState() {
        webUI.pressTab(TXT_STATE);
    }

    public void pressTabOnCustomerPin() {
        webUI.pressTab(TXT_PIN);
    }

    public void pressTabOnCustomerMobile() {
        webUI.pressTab(TXT_MOBILE);
    }

    public void pressTabOnCustomerEmail() {
        webUI.pressTab(TXT_EMAIL);
    }

    public void pressTabOnCustomerPassword() {
        webUI.pressTab(TXT_PASSWORD);
    }

    public String getSuccessMessage() {
        webUI.waitForElementVisible(LBL_SUCCESS_MSG, 10);
        return webUI.getText(LBL_SUCCESS_MSG);
    }
}