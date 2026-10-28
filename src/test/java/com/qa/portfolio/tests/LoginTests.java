package com.qa.portfolio.tests;

import static com.qa.portfolio.utils.TestData.LOCKED_OUT_USER;
import static com.qa.portfolio.utils.TestData.PASSWORD;
import static com.qa.portfolio.utils.TestData.STANDARD_USER;

import com.qa.portfolio.base.BaseTest;
import com.qa.portfolio.pages.InventoryPage;
import com.qa.portfolio.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(description = "TC_LOGIN_01 - Valid user can log in", groups = {"smoke", "login"})
    public void validUserCanLogIn() {
        InventoryPage inventory = new LoginPage(driver).loginAs(STANDARD_USER, PASSWORD);

        Assert.assertEquals(inventory.getTitle().toLowerCase(), "products", "Page title after login");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Should land on the products page");
    }

    @Test(description = "TC_LOGIN_02 - Wrong password shows an error", groups = {"regression", "login"})
    public void wrongPasswordShowsError() {
        String error = new LoginPage(driver)
                .loginExpectingFailure(STANDARD_USER, "wrong_password")
                .getErrorMessage();

        Assert.assertTrue(error.contains("do not match"), "Unexpected error message: " + error);
    }

    @Test(description = "TC_LOGIN_03 - Locked out user cannot log in", groups = {"regression", "login"})
    public void lockedOutUserCannotLogIn() {
        String error = new LoginPage(driver)
                .loginExpectingFailure(LOCKED_OUT_USER, PASSWORD)
                .getErrorMessage();

        Assert.assertTrue(error.contains("locked out"), "Unexpected error message: " + error);
    }

    @Test(description = "TC_LOGIN_04 - Empty username shows an error", groups = {"regression", "login"})
    public void emptyUsernameShowsError() {
        String error = new LoginPage(driver)
                .loginExpectingFailure("", PASSWORD)
                .getErrorMessage();

        Assert.assertTrue(error.contains("Username is required"), "Unexpected error message: " + error);
    }

    @Test(description = "TC_LOGIN_05 - Empty password shows an error", groups = {"regression", "login"})
    public void emptyPasswordShowsError() {
        String error = new LoginPage(driver)
                .loginExpectingFailure(STANDARD_USER, "")
                .getErrorMessage();

        Assert.assertTrue(error.contains("Password is required"), "Unexpected error message: " + error);
    }

    @Test(description = "TC_LOGIN_06 - User can log out", groups = {"regression", "login"})
    public void userCanLogOut() {
        LoginPage loginPage = loginAsStandardUser().logout();

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be visible after logout");
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory.html"), "Should have left the products page");
    }
}
