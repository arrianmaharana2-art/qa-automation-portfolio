package com.qa.portfolio.tests;

import static com.qa.portfolio.utils.TestData.BACKPACK;
import static com.qa.portfolio.utils.TestData.BIKE_LIGHT;
import static com.qa.portfolio.utils.TestData.FIRST_NAME;
import static com.qa.portfolio.utils.TestData.LAST_NAME;
import static com.qa.portfolio.utils.TestData.POSTAL_CODE;

import com.qa.portfolio.base.BaseTest;
import com.qa.portfolio.pages.CartPage;
import com.qa.portfolio.pages.CheckoutPage;
import com.qa.portfolio.pages.InventoryPage;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {

    @Test(description = "TC_CART_01 - Cart shows the products that were added", groups = {"regression", "cart"})
    public void cartShowsAddedProducts() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart(BACKPACK).addToCart(BIKE_LIGHT);

        List<String> itemsInCart = inventory.openCart().getItemNames();

        Assert.assertEquals(itemsInCart.size(), 2, "Number of items in cart");
        Assert.assertTrue(itemsInCart.contains(BACKPACK), BACKPACK + " should be in the cart");
        Assert.assertTrue(itemsInCart.contains(BIKE_LIGHT), BIKE_LIGHT + " should be in the cart");
    }

    @Test(description = "TC_CHK_01 - User can complete an order end to end", groups = {"smoke", "checkout"})
    public void userCanCompleteOrderEndToEnd() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart(BACKPACK);

        CartPage cart = inventory.openCart();
        Assert.assertTrue(cart.getItemNames().contains(BACKPACK), "Backpack should be in the cart");

        CheckoutPage checkout = cart.proceedToCheckout()
                .fillInformation(FIRST_NAME, LAST_NAME, POSTAL_CODE)
                .continueToOverview();
        Assert.assertTrue(checkout.getTotalLabel().contains("Total"), "Order overview should show a total");

        String confirmation = checkout.finishOrder().getConfirmationHeader();
        Assert.assertTrue(confirmation.toLowerCase().contains("thank you for your order"),
                "Unexpected confirmation message: " + confirmation);
    }

    @Test(description = "TC_CHK_02 - Checkout without first name shows an error", groups = {"regression", "checkout"})
    public void checkoutWithoutFirstNameShowsError() {
        InventoryPage inventory = loginAsStandardUser();
        inventory.addToCart(BACKPACK);

        String error = inventory.openCart()
                .proceedToCheckout()
                .fillInformation("", LAST_NAME, POSTAL_CODE)
                .continueToOverview()
                .getErrorMessage();

        Assert.assertTrue(error.contains("First Name is required"), "Unexpected error message: " + error);
    }
}
