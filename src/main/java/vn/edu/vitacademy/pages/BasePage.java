package vn.edu.vitacademy.pages;

import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.components.LeftMenu;

public class BasePage {

  protected WebUI webUI;

  public BasePage(WebUI webUI) {
    this.webUI = webUI;
  }



  public LeftMenu leftMenu() {
    return new LeftMenu(webUI);
  }

  /**
   * Lấy tiêu đề (Title) của trang hiện tại từ trình duyệt.
   * Phương thức này được đặt ở BasePage để tất cả các Page con đều có thể sử dụng.
   *
   * @return Chuỗi tiêu đề trang hiện tại
   */
  public String getPageTitle() {
    return webUI.getPageTitle();
  }
}
