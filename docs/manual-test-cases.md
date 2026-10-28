# Manual Test Cases (SauceDemo)

These are the manual test cases the automated tests are based on. The "Automated test" column links each one to the Selenium test that covers it. Cases marked **Manual only** need a human eye and are not automated.

Test data (published on the SauceDemo login page): users `standard_user` and `locked_out_user`, password `secret_sauce`.

| ID | Scenario | Steps | Expected result | Type | Automated test |
|----|----------|-------|-----------------|------|----------------|
| TC_LOGIN_01 | Valid login | 1. Open the site. 2. Enter `standard_user` and `secret_sauce`. 3. Click Login. | Products page opens | Positive | `LoginTests.validUserCanLogIn` |
| TC_LOGIN_02 | Wrong password | 1. Enter `standard_user` and a wrong password. 2. Click Login. | Error says username and password do not match | Negative | `LoginTests.wrongPasswordShowsError` |
| TC_LOGIN_03 | Locked out user | 1. Enter `locked_out_user` and `secret_sauce`. 2. Click Login. | Error says the user is locked out | Negative | `LoginTests.lockedOutUserCannotLogIn` |
| TC_LOGIN_04 | Empty username | 1. Leave username empty, enter a password. 2. Click Login. | Error says username is required | Negative | `LoginTests.emptyUsernameShowsError` |
| TC_LOGIN_05 | Empty password | 1. Enter a username, leave password empty. 2. Click Login. | Error says password is required | Negative | `LoginTests.emptyPasswordShowsError` |
| TC_LOGIN_06 | Logout | 1. Log in. 2. Open the menu. 3. Click Logout. | Login page is shown | Positive | `LoginTests.userCanLogOut` |
| TC_INV_01 | Product list | 1. Log in. | Six products are listed | Positive | `InventoryTests.productsPageListsSixProducts` |
| TC_INV_02 | Add to cart | 1. Log in. 2. Click Add to cart on two products. | Cart badge shows 1, then 2 | Positive | `InventoryTests.addingProductsUpdatesCartBadge` |
| TC_INV_03 | Remove from cart | 1. Add a product. 2. Click Remove. | Cart badge disappears | Positive | `InventoryTests.removingProductClearsCartBadge` |
| TC_INV_04 | Sort by name Z to A | 1. Log in. 2. Choose "Name (Z to A)". | Products are in reverse alphabetical order | Positive | `InventoryTests.productsCanBeSortedByNameZToA` |
| TC_INV_05 | Sort by price low to high | 1. Log in. 2. Choose "Price (low to high)". | Prices go from lowest to highest | Positive | `InventoryTests.productsCanBeSortedByPriceLowToHigh` |
| TC_CART_01 | Cart contents | 1. Add two products. 2. Open the cart. | Both products are listed | Positive | `CheckoutTests.cartShowsAddedProducts` |
| TC_CHK_01 | Complete an order | 1. Add a product. 2. Open the cart and click Checkout. 3. Enter name and postal code. 4. Continue, then Finish. | "Thank you for your order" message | Positive | `CheckoutTests.userCanCompleteOrderEndToEnd` |
| TC_CHK_02 | Missing first name | 1. Add a product and start checkout. 2. Leave first name empty. 3. Click Continue. | Error says first name is required | Negative | `CheckoutTests.checkoutWithoutFirstNameShowsError` |
| TC_UX_01 | Look and feel | Review product images, text alignment and layout on the products page. | Everything is readable and aligned | Usability | **Manual only** |
| TC_UX_02 | Browser window resize | Resize the browser window on the products page. | Layout adjusts without overlapping | Usability | **Manual only** |
