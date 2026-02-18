package com.ecommerce.tests.exercises;

import com.ecommerce.api.models.Cart;
import com.ecommerce.api.queries.GraphQLQueries;
import com.ecommerce.tests.BaseGraphQLTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * EXERCISE 2: Cart Operations Testing (20 minutes)
 * 
 * Scenario: Test shopping cart mutations and state management
 * 
 * TASKS:
 * 1. Implement test to add item to cart
 *    - Generate a unique user ID
 *    - Add product "PROD-001" with quantity 2 to cart
 *    - Verify cart is created with correct items
 *    - Verify totalPrice is calculated correctly
 * 
 * 2. Implement test to update cart item quantity
 *    - First add a product to cart
 *    - Then update its quantity to 5
 *    - Verify the quantity is updated
 *    - Verify totalPrice is recalculated correctly
 * 
 * 3. Implement test to remove item from cart
 *    - Add multiple products to cart
 *    - Remove one product
 *    - Verify the product is removed
 *    - Verify cart still contains other items
 * 
 * 4. Implement test for adding item with invalid product ID
 *    - Try to add product "INVALID-PROD" to cart
 *    - Verify error handling
 * 
 * HINTS:
 * - Use generateUserId() to create unique user IDs
 * - Response path for cart: "data.addToCart", "data.cart", "data.updateCartItem"
 * - Cart has items list with subtotal for each item
 * - totalPrice = sum of all item subtotals
 * - You may need to chain multiple mutations to test workflows
 */
public class Exercise2_CartOperations extends BaseGraphQLTest {

    @Test
    public void testAddItemToCart() {
        // TODO: Implement this test
        // Step 1: Generate a unique user ID
        
        // Step 2: Execute mutation to add product "PROD-001" with quantity 2
        
        // Step 3: Verify response status and no errors
        
        // Step 4: Extract cart from response
        
        // Step 5: Assert cart is not null and has items
        
        // Step 6: Verify the product was added with correct quantity
        
        // Step 7: Verify totalPrice is calculated (should be price * quantity)
        
        fail("Test not implemented yet");
    }

    @Test
    public void testUpdateCartItemQuantity() {
        // TODO: Implement this test
        // Step 1: Setup - Add a product to cart first
        
        // Step 2: Update the cart item quantity to 5
        
        // Step 3: Verify the quantity is updated
        
        // Step 4: Verify totalPrice is recalculated
        
        fail("Test not implemented yet");
    }

    @Test
    public void testRemoveItemFromCart() {
        // TODO: Implement this test
        // Step 1: Setup - Add 2 different products to cart
        
        // Step 2: Remove one product from cart
        
        // Step 3: Verify the removed product is no longer in cart
        
        // Step 4: Verify the other product is still in cart
        
        // Step 5: Verify totalPrice is updated correctly
        
        fail("Test not implemented yet");
    }

    @Test
    public void testAddInvalidProductToCart() {
        // TODO: Implement this test
        // Step 1: Try to add invalid product "INVALID-PROD"
        
        // Step 2: Verify error handling (should have errors or null cart)
        
        fail("Test not implemented yet");
    }

    // BONUS: Test edge cases
    @Test
    public void testAddItemWithZeroQuantity() {
        // TODO: Optional - test validation for quantity = 0
    }

    @Test
    public void testAddItemWithNegativeQuantity() {
        // TODO: Optional - test validation for negative quantity
    }
}
