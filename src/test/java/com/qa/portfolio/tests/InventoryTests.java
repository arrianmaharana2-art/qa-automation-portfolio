package com.qa.portfolio.tests;

import static com.qa.portfolio.utils.TestData.BACKPACK;
import static com.qa.portfolio.utils.TestData.BIKE_LIGHT;

import com.qa.portfolio.base.BaseTest;
import com.qa.portfolio.pages.InventoryPage;
import com.qa.portfolio.pages.InventoryPage.SortOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InventoryTests extends BaseTest {

    @Test(description = "TC_INV_01 - Products page lists six products", groups = {"smoke", "inventory"})
    public void productsPageListsSixProducts() {
        InventoryPage inventory = loginAsStandardUser();

        Assert.assertEquals(inventory.getProductCount(), 6, "Number of products");
    }

    @Test(description = "TC_INV_02 - Adding products updates the cart badge", groups = {"regression", "inventory"})
    public void addingProductsUpdatesCartBadge() {
        InventoryPage inventory = loginAsStandardUser();

        inventory.addToCart(BACKPACK);
        Assert.assertEquals(inventory.getCartCount(), 1, "Cart badge after first product");

        inventory.addToCart(BIKE_LIGHT);
        Assert.assertEquals(inventory.getCartCount(), 2, "Cart badge after second product");
    }

    @Test(description = "TC_INV_03 - Removing a product clears the cart badge", groups = {"regression", "inventory"})
    public void removingProductClearsCartBadge() {
        InventoryPage inventory = loginAsStandardUser();

        inventory.addToCart(BACKPACK);
        inventory.removeFromCart(BACKPACK);

        Assert.assertEquals(inventory.getCartCount(), 0, "Cart badge after removing the only product");
    }

    @Test(description = "TC_INV_04 - Sort by name Z to A", groups = {"regression", "inventory"})
    public void productsCanBeSortedByNameZToA() {
        InventoryPage inventory = loginAsStandardUser();

        List<String> expected = new ArrayList<>(inventory.getProductNames());
        Collections.sort(expected, Collections.reverseOrder());

        inventory.sortBy(SortOption.NAME_Z_TO_A);

        Assert.assertEquals(inventory.getProductNames(), expected, "Products should be in Z to A order");
    }

    @Test(description = "TC_INV_05 - Sort by price low to high", groups = {"regression", "inventory"})
    public void productsCanBeSortedByPriceLowToHigh() {
        InventoryPage inventory = loginAsStandardUser();

        inventory.sortBy(SortOption.PRICE_LOW_TO_HIGH);
        List<Double> prices = inventory.getProductPrices();

        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1),
                    "Price " + prices.get(i) + " should not be higher than " + prices.get(i + 1));
        }
    }
}
