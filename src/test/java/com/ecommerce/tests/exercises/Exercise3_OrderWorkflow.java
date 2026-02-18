package com.ecommerce.tests.exercises;

import com.ecommerce.api.queries.GraphQLQueries;
import com.ecommerce.tests.BaseGraphQLTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

/**
 * EXERCISE 3: End-to-End Order Workflow Testing (BONUS - 15 minutes)
 * 
 * Scenario: Test the complete checkout flow from cart to order
 * 
 * TASKS:
 * 1. Implement end-to-end order creation workflow
 *    - Add multiple products to cart
 *    - Create an order from the cart
 *    - Verify order is created with correct details
 *    - Verify order status is "PENDING"
 *    - Verify all cart items are in the order
 * 
 * 2. Implement test to retrieve order by ID
 *    - Create an order first
 *    - Retrieve the order using its ID
 *    - Verify all order details match
 * 
 * This exercise tests your ability to:
 * - Chain multiple API calls in a workflow
 * - Maintain state across operations
 * - Validate complex response structures
 * - Handle end-to-end scenarios
 * 
 * HINTS:
 * - Order creation path: "data.createOrder"
 * - Order query path: "data.order"
 * - Order has: id, userId, orderDate, status, items, totalPrice, shippingAddress
 * - Status should be "PENDING" for new orders
 */
public class Exercise3_OrderWorkflow extends BaseGraphQLTest {

    @Test
    public void testCompleteOrderWorkflow() {
        // TODO: Implement this test
        // Step 1: Generate unique user ID
        
        // Step 2: Add 2-3 products to cart
        
        // Step 3: Verify cart has all items and correct total
        
        // Step 4: Create order from cart
        String shippingAddress = "123 Main St, City, State 12345";
        
        // Step 5: Verify order is created successfully
        
        // Step 6: Extract order details
        
        // Step 7: Assert order has correct userId
        
        // Step 8: Assert order status is "PENDING"
        
        // Step 9: Assert order has all cart items
        
        // Step 10: Assert order totalPrice matches cart total
        
        // Step 11: Assert shipping address is correct
        
        fail("Test not implemented yet");
    }

    @Test
    public void testRetrieveOrderById() {
        // TODO: Implement this test
        // Step 1: Create an order first (setup)
        
        // Step 2: Extract order ID from creation response
        
        // Step 3: Query the order using its ID
        
        // Step 4: Verify retrieved order matches created order
        
        fail("Test not implemented yet");
    }

    // BONUS: Test order validation
    @Test
    public void testCreateOrderWithEmptyCart() {
        // TODO: Optional - test creating order when cart is empty
        // Should return error or validation message
    }

    @Test
    public void testCreateOrderWithInvalidShippingAddress() {
        // TODO: Optional - test validation of shipping address
    }
}
