package Guru99.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginTest.class);

    @Test(description = "LG002 - Kiểm tra đăng ký và đăng nhập thành công (hoặc sử dụng tài khoản đã lưu)")
    public void LG002_verify_navigate_to_register_page() {
        loginPage().loginWithValidAccount(baseUrl);

        // Chờ trang chủ Manager load xong (bằng cách chờ link Log out hiển thị trên Left Menu)
        Assert.assertTrue(loginPage().leftMenu().isLogoutLinkDisplayed(), "Lỗi: Không tìm thấy link Log out sau khi đăng nhập!");

        // 2. Kiểm tra đăng nhập thành công vào trang chủ Manager thông qua tiêu đề trang của Page
        Assert.assertEquals(loginPage().getPageTitle(), "Guru99 Bank Manager HomePage", "Lỗi: Đăng nhập không thành công vào trang chủ Manager!");
    }
}