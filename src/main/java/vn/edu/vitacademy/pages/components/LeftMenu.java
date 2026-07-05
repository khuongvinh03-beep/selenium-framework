package vn.edu.vitacademy.pages.components;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.NewCustomerPage;
import vn.edu.vitacademy.common.helper.JsonHelper;

public class LeftMenu {

  private final JsonHelper locators = new JsonHelper("LeftMenu.json");
  private final String LNK_NEW_CUSTOMER = locators.getLocator("LNK_NEW_CUSTOMER");
  private final String LNK_LOG_OUT = locators.getLocator("LNK_LOG_OUT");

  private WebUI webUI;

  public LeftMenu(WebUI webUI) {
    this.webUI = webUI;
  }

  public NewCustomerPage moveToNewCustomer() {
    webUI.clickOn(LNK_NEW_CUSTOMER);
    webUI.sleep(2);
    return new NewCustomerPage(webUI);
  }

  public void clickLogOut() {
    webUI.clickOn(LNK_LOG_OUT);
  }

  /**
   * Kiểm tra xem liên kết "Log out" có hiển thị trên menu trái hay không.
   * Đây là dấu hiệu nhận biết hệ thống đã đăng nhập thành công vào trang chủ Manager.
   *
   * @return true nếu liên kết Log out hiển thị trong vòng 15 giây, ngược lại false
   */
  public boolean isLogoutLinkDisplayed() {
    return webUI.waitForElementVisible(LNK_LOG_OUT, 15);
  }
}
