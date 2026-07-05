package Guru99.pages;

import Guru99.common.keywords.WebUI;
import Guru99.common.helper.JsonHelper;

public class NewCustomerPage extends BasePage {

    private final JsonHelper locators = new JsonHelper("NewCustomerPage.json");

    public NewCustomerPage(WebUI webUI) {
        super(webUI);
    }

    // nhập dữ liệu
    public void inputCustomerName(String name) {
        webUI.clearText(locators.getLocator("TXT_CUSTOMER_NAME"));
        webUI.inputText(locators.getLocator("TXT_CUSTOMER_NAME"), name);
    }

    public void inputCustomerAddress(String address) {
        webUI.clearText(locators.getLocator("TXT_ADDRESS"));
        webUI.inputText(locators.getLocator("TXT_ADDRESS"), address);
    }

    public void inputCustomerCity(String city) {
        webUI.clearText(locators.getLocator("TXT_CITY"));
        webUI.inputText(locators.getLocator("TXT_CITY"), city);
    }

    public void inputCustomerState(String state) {
        webUI.clearText(locators.getLocator("TXT_STATE"));
        webUI.inputText(locators.getLocator("TXT_STATE"), state);
    }

    public void inputCustomerPin(String pin) {
        webUI.clearText(locators.getLocator("TXT_PIN"));
        webUI.inputText(locators.getLocator("TXT_PIN"), pin);
    }

    public void inputCustomerMobile(String mobile) {
        webUI.clearText(locators.getLocator("TXT_MOBILE"));
        webUI.inputText(locators.getLocator("TXT_MOBILE"), mobile);
    }

    public void inputCustomerEmail(String email) {
        webUI.clearText(locators.getLocator("TXT_EMAIL"));
        webUI.inputText(locators.getLocator("TXT_EMAIL"), email);
    }

    public void inputCustomerPassword(String password) {
        webUI.clearText(locators.getLocator("TXT_PASSWORD"));
        webUI.inputText(locators.getLocator("TXT_PASSWORD"), password);
    }

    // create
    public void createCustomer(String name, String gender, String dob, String address, String city, String state,
            String pinCode, String mobileNo, String email, String password) {
        webUI.inputText(locators.getLocator("TXT_CUSTOMER_NAME"), name);

        if ("male".equalsIgnoreCase(gender) || "m".equalsIgnoreCase(gender)) {
            webUI.clickOn(locators.getLocator("RAD_GENDER_MALE"));
        } else {
            webUI.clickOn(locators.getLocator("RAD_GENDER_FEMALE"));
        }
        webUI.inputText(locators.getLocator("TXT_DOB"), dob);
        webUI.inputText(locators.getLocator("TXT_ADDRESS"), address);
        webUI.inputText(locators.getLocator("TXT_CITY"), city);
        webUI.inputText(locators.getLocator("TXT_STATE"), state);
        webUI.inputText(locators.getLocator("TXT_PIN"), pinCode);
        webUI.inputText(locators.getLocator("TXT_MOBILE"), mobileNo);
        webUI.inputText(locators.getLocator("TXT_EMAIL"), email);
        webUI.inputText(locators.getLocator("TXT_PASSWORD"), password);
        webUI.clickOn(locators.getLocator("BTN_SUBMIT"));
    }

    // lấy validate lỗi
    public String getCustomerNameValidationError() {
        webUI.waitForElementVisible(locators.getLocator("LBL_NAME_ERROR"), 5);
        return webUI.getText(locators.getLocator("LBL_NAME_ERROR"));
    }

    public String getCustomerAddressValidationError() {
        webUI.waitForElementVisible(locators.getLocator("LBL_ADDRESS_ERROR"), 5);
        return webUI.getText(locators.getLocator("LBL_ADDRESS_ERROR"));
    }

    public String getCustomerCityValidationError() {
        webUI.waitForElementVisible(locators.getLocator("LBL_CITY_ERROR"), 5);
        return webUI.getText(locators.getLocator("LBL_CITY_ERROR"));
    }

    public String getCustomerStateValidationError() {
        webUI.waitForElementVisible(locators.getLocator("LBL_STATE_ERROR"), 5);
        return webUI.getText(locators.getLocator("LBL_STATE_ERROR"));
    }

    public String getCustomerPinValidationError() {
        webUI.waitForElementVisible(locators.getLocator("LBL_PIN_ERROR"), 5);
        return webUI.getText(locators.getLocator("LBL_PIN_ERROR"));
    }

    public String getCustomerMobileValidationError() {
        webUI.waitForElementVisible(locators.getLocator("LBL_MOBILE_ERROR"), 5);
        return webUI.getText(locators.getLocator("LBL_MOBILE_ERROR"));
    }

    public String getCustomerEmailValidationError() {
        webUI.waitForElementVisible(locators.getLocator("LBL_EMAIL_ERROR"), 5);
        return webUI.getText(locators.getLocator("LBL_EMAIL_ERROR"));
    }

    public String getCustomerPasswordValidationError() {
        webUI.waitForElementVisible(locators.getLocator("LBL_PASSWORD_ERROR"), 5);
        return webUI.getText(locators.getLocator("LBL_PASSWORD_ERROR"));
    }

    public String getTelephoneLabel() {
        return webUI.getText(locators.getLocator("LBL_MOBILE_LABEL"));
    }

    // thực hiện tab
    public void pressTabOnCustomerName() {
        webUI.pressTab(locators.getLocator("TXT_CUSTOMER_NAME"));
    }

    public void pressTabOnCustomerAddress() {
        webUI.pressTab(locators.getLocator("TXT_ADDRESS"));
    }

    public void pressTabOnCustomerCity() {
        webUI.pressTab(locators.getLocator("TXT_CITY"));
    }

    public void pressTabOnCustomerState() {
        webUI.pressTab(locators.getLocator("TXT_STATE"));
    }

    public void pressTabOnCustomerPin() {
        webUI.pressTab(locators.getLocator("TXT_PIN"));
    }

    public void pressTabOnCustomerMobile() {
        webUI.pressTab(locators.getLocator("TXT_MOBILE"));
    }

    public void pressTabOnCustomerEmail() {
        webUI.pressTab(locators.getLocator("TXT_EMAIL"));
    }

    public void pressTabOnCustomerPassword() {
        webUI.pressTab(locators.getLocator("TXT_PASSWORD"));
    }

    public String getSuccessMessage() {
        webUI.waitForElementVisible(locators.getLocator("LBL_SUCCESS_MSG"), 10);
        return webUI.getText(locators.getLocator("LBL_SUCCESS_MSG"));
    }
}