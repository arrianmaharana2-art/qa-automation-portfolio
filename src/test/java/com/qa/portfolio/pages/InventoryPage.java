package com.qa.portfolio.pages;

import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/** The products page shown after a successful login. */
public class InventoryPage extends BasePage {

    /** The values below are the option values used by the site's sort dropdown. */
    public enum SortOption {
        NAME_A_TO_Z("az"),
        NAME_Z_TO_A("za"),
        PRICE_LOW_TO_HIGH("lohi"),
        PRICE_HIGH_TO_LOW("hilo");

        private final String value;

        SortOption(String value) {
            this.value = value;
        }
    }

    private final By pageTitle = By.className("title");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public int getProductCount() {
        waitForVisible(productNames);
        return driver.findElements(productNames).size();
    }

    public List<String> getProductNames() {
        waitForVisible(productNames);
        return getTexts(productNames);
    }

    public List<Double> getProductPrices() {
        waitForVisible(productPrices);
        return getTexts(productPrices).stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .collect(Collectors.toList());
    }

    public InventoryPage addToCart(String productName) {
        click(By.id("add-to-cart-" + toSlug(productName)));
        return this;
    }

    public InventoryPage removeFromCart(String productName) {
        click(By.id("remove-" + toSlug(productName)));
        return this;
    }

    /** Returns the number on the cart badge, or 0 when the badge is not shown. */
    public int getCartCount() {
        List<?> badges = driver.findElements(cartBadge);
        if (badges.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(getText(cartBadge));
    }

    public InventoryPage sortBy(SortOption option) {
        new Select(waitForVisible(sortDropdown)).selectByValue(option.value);
        return this;
    }

    public CartPage openCart() {
        click(cartLink);
        return new CartPage(driver);
    }

    public LoginPage logout() {
        click(menuButton);
        click(logoutLink);
        return new LoginPage(driver);
    }

    /** "Sauce Labs Backpack" becomes "sauce-labs-backpack", which is how the site names its button ids. */
    private String toSlug(String productName) {
        return productName.toLowerCase().replace(" ", "-");
    }
}
