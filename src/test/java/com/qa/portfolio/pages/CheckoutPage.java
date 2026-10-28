package com.qa.portfolio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Covers the three checkout screens: your information, overview and confirmation. */
public class CheckoutPage extends BasePage {

    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By totalLabel = By.className("summary_total_label");
    private final By confirmationHeader = By.className("complete-header");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage fillInformation(String firstName, String lastName, String postalCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, postalCode);
        return this;
    }

    public CheckoutPage continueToOverview() {
        click(continueButton);
        return this;
    }

    public String getTotalLabel() {
        return getText(totalLabel);
    }

    public CheckoutPage finishOrder() {
        click(finishButton);
        return this;
    }

    public String getConfirmationHeader() {
        return getText(confirmationHeader);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
