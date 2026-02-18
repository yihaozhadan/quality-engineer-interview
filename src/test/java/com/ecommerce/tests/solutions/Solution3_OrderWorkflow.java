package com.ecommerce.tests.solutions;

import com.ecommerce.api.queries.GraphQLQueries;
import com.ecommerce.tests.BaseGraphQLTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;

/**
 * SOLUTION FOR EXERCISE 3: End-to-End Order Workflow Testing
 * 
 * This is a reference solution for the interviewer.
 * 
 * Key evaluation points:
 * - Ability to design and implement multi-step workflows
 * - Proper state management across multiple API calls
 * - Validation of complex nested data structures
 * - Understanding of e-commerce business logic
 * - Clean test organization and meaningful assertions
 * - Realistic test scenarios and edge cases
 */
public class Solution3_OrderWorkflow extends BaseGraphQLTest {

    @Test
    @DisplayName("Complete order workflow - from cart to order creation")
    public void testCompleteOrderWorkflow() {
        // Step 1: Generate unique user ID for test isolation
        String userId = generateUserId();

        // Step 2: Add 2-3 products to cart
        // Add product 1
        String addMutation1 = GraphQLQueries.addToCart(userId, "PROD-001", 2);
        Response addResponse1 = executeGraphQL(addMutation1);
        assertStatusCode(addResponse1, 200);
        assertNoErrors(addResponse1);

        // Add product 2
        String addMutation2 = GraphQLQueries.addToCart(userId, "PROD-002", 1);
        Response addResponse2 = executeGraphQL(addMutation2);
        assertStatusCode(addResponse2, 200);
        assertNoErrors(addResponse2);

        // Add product 3
        String addMutation3 = GraphQLQueries.addToCart(userId, "PROD-003", 3);
        Response addResponse3 = executeGraphQL(addMutation3);
        assertStatusCode(addResponse3, 200);
        assertNoErrors(addResponse3);

        // Step 3: Verify cart has all items and correct total
        String getCartQuery = GraphQLQueries.getCart(userId);
        Response cartResponse = executeGraphQL(getCartQuery);
        
        assertStatusCode(cartResponse, 200);
        assertNoErrors(cartResponse);

        Map<String, Object> cart = cartResponse.jsonPath().getMap("data.cart");
        List<Map<String, Object>> cartItems = (List<Map<String, Object>>) cart.get("items");
        Double cartTotal = ((Number) cart.get("totalPrice")).doubleValue();

        assertThat(cartItems)
            .as("Cart should have 3 items")
            .hasSize(3);

        // Step 4: Create order from cart
        String shippingAddress = "123 Main St, City, State 12345";
        String createOrderMutation = GraphQLQueries.createOrder(userId, shippingAddress);
        Response orderResponse = executeGraphQL(createOrderMutation);

        // Step 5: Verify order is created successfully
        assertStatusCode(orderResponse, 200);
        assertNoErrors(orderResponse);

        // Step 6: Extract order details
        Map<String, Object> order = orderResponse.jsonPath().getMap("data.createOrder");
        
        assertThat(order)
            .as("Order should be created")
            .isNotNull();

        String orderId = (String) order.get("id");
        assertThat(orderId)
            .as("Order should have an ID")
            .isNotNull()
            .isNotEmpty();

        // Step 7: Assert order has correct userId
        assertThat(order.get("userId"))
            .as("Order should belong to correct user")
            .isEqualTo(userId);

        // Step 8: Assert order status is "PENDING"
        assertThat(order.get("status"))
            .as("New order should have PENDING status")
            .isEqualTo("PENDING");

        // Step 9: Assert order has all cart items
        List<Map<String, Object>> orderItems = (List<Map<String, Object>>) order.get("items");
        assertThat(orderItems)
            .as("Order should have all cart items")
            .hasSize(3);

        // Verify each item from cart is in the order
        List<String> cartProductIds = cartItems.stream()
            .map(item -> (String) item.get("productId"))
            .toList();
        
        List<String> orderProductIds = orderItems.stream()
            .map(item -> (String) item.get("productId"))
            .toList();
        
        assertThat(orderProductIds)
            .as("Order should contain all products from cart")
            .containsExactlyInAnyOrderElementsOf(cartProductIds);

        // Step 10: Assert order totalPrice matches cart total
        Double orderTotal = ((Number) order.get("totalPrice")).doubleValue();
        assertThat(orderTotal)
            .as("Order total should match cart total")
            .isEqualTo(cartTotal);

        // Step 11: Assert shipping address is correct
        assertThat(order.get("shippingAddress"))
            .as("Order should have correct shipping address")
            .isEqualTo(shippingAddress);

        // Additional validation: order should have a timestamp
        assertThat(order.get("orderDate"))
            .as("Order should have a creation timestamp")
            .isNotNull();
    }

    @Test
    @DisplayName("Retrieve order by ID - should return complete order details")
    public void testRetrieveOrderById() {
        // Step 1: Create an order first (setup)
        String userId = generateUserId();
        
        // Add items to cart
        executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-001", 2));
        executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-002", 1));
        
        // Create order
        String shippingAddress = "456 Oak Ave, Town, State 67890";
        String createOrderMutation = GraphQLQueries.createOrder(userId, shippingAddress);
        Response createResponse = executeGraphQL(createOrderMutation);
        
        assertStatusCode(createResponse, 200);
        assertNoErrors(createResponse);

        // Step 2: Extract order ID from creation response
        Map<String, Object> createdOrder = createResponse.jsonPath().getMap("data.createOrder");
        String orderId = (String) createdOrder.get("id");
        Double createdTotal = ((Number) createdOrder.get("totalPrice")).doubleValue();
        
        assertThat(orderId).isNotNull();

        // Step 3: Query the order using its ID
        String getOrderQuery = GraphQLQueries.getOrder(orderId);
        Response getResponse = executeGraphQL(getOrderQuery);
        
        assertStatusCode(getResponse, 200);
        assertNoErrors(getResponse);

        // Step 4: Verify retrieved order matches created order
        Map<String, Object> retrievedOrder = getResponse.jsonPath().getMap("data.order");
        
        assertThat(retrievedOrder)
            .as("Order should be retrieved successfully")
            .isNotNull();

        // Verify all key fields match
        assertThat(retrievedOrder.get("id"))
            .as("Order ID should match")
            .isEqualTo(orderId);
        
        assertThat(retrievedOrder.get("userId"))
            .as("User ID should match")
            .isEqualTo(userId);
        
        assertThat(retrievedOrder.get("status"))
            .as("Status should match")
            .isEqualTo(createdOrder.get("status"));
        
        Double retrievedTotal = ((Number) retrievedOrder.get("totalPrice")).doubleValue();
        assertThat(retrievedTotal)
            .as("Total price should match")
            .isEqualTo(createdTotal);
        
        assertThat(retrievedOrder.get("shippingAddress"))
            .as("Shipping address should match")
            .isEqualTo(shippingAddress);

        // Verify items count matches
        List<Map<String, Object>> retrievedItems = (List<Map<String, Object>>) retrievedOrder.get("items");
        List<Map<String, Object>> createdItems = (List<Map<String, Object>>) createdOrder.get("items");
        
        assertThat(retrievedItems)
            .as("Items count should match")
            .hasSameSizeAs(createdItems);
    }

    @Test
    @DisplayName("BONUS: Create order with empty cart - should return error")
    public void testCreateOrderWithEmptyCart() {
        // Try to create order when cart is empty (or doesn't exist)
        String userId = generateUserId(); // New user with no cart
        String shippingAddress = "123 Empty Cart St";
        
        String createOrderMutation = GraphQLQueries.createOrder(userId, shippingAddress);
        Response response = executeGraphQL(createOrderMutation);
        
        assertStatusCode(response, 200); // GraphQL returns 200
        
        // Should have errors or null order
        boolean hasValidationError = hasErrors(response) || 
            response.jsonPath().getMap("data.createOrder") == null;
        
        assertThat(hasValidationError)
            .as("Should not allow order creation with empty cart")
            .isTrue();
        
        if (hasErrors(response)) {
            String errorMessage = getErrorMessage(response);
            assertThat(errorMessage)
                .as("Error message should indicate empty cart issue")
                .containsAnyOf("empty", "cart", "items");
        }
    }

    @Test
    @DisplayName("BONUS: Create order with invalid shipping address - should validate")
    public void testCreateOrderWithInvalidShippingAddress() {
        String userId = generateUserId();
        
        // Add item to cart
        executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-001", 1));
        
        // Try to create order with empty/invalid address
        String invalidAddress = "";
        String createOrderMutation = GraphQLQueries.createOrder(userId, invalidAddress);
        Response response = executeGraphQL(createOrderMutation);
        
        assertStatusCode(response, 200);
        
        // Should have validation error for invalid address
        boolean hasValidationError = hasErrors(response) || 
            response.jsonPath().getMap("data.createOrder") == null;
        
        assertThat(hasValidationError)
            .as("Should validate shipping address")
            .isTrue();
    }

    @Test
    @DisplayName("BONUS: Verify order items match cart items exactly")
    public void testOrderItemsMatchCartItems() {
        String userId = generateUserId();
        
        // Add specific items to cart
        executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-001", 3));
        executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-002", 2));
        
        // Get cart state
        Response cartResponse = executeGraphQL(GraphQLQueries.getCart(userId));
        List<Map<String, Object>> cartItems = cartResponse.jsonPath()
            .getList("data.cart.items");
        
        // Create order
        String createOrderMutation = GraphQLQueries.createOrder(userId, "123 Test St");
        Response orderResponse = executeGraphQL(createOrderMutation);
        
        List<Map<String, Object>> orderItems = orderResponse.jsonPath()
            .getList("data.createOrder.items");
        
        // Verify each cart item is in order with same quantity and price
        for (Map<String, Object> cartItem : cartItems) {
            String productId = (String) cartItem.get("productId");
            int cartQty = (Integer) cartItem.get("quantity");
            double cartSubtotal = ((Number) cartItem.get("subtotal")).doubleValue();
            
            // Find matching order item
            Map<String, Object> matchingOrderItem = orderItems.stream()
                .filter(item -> productId.equals(item.get("productId")))
                .findFirst()
                .orElse(null);
            
            assertThat(matchingOrderItem)
                .as("Order should contain item: " + productId)
                .isNotNull();
            
            assertThat(matchingOrderItem.get("quantity"))
                .as("Quantity should match for: " + productId)
                .isEqualTo(cartQty);
            
            double orderSubtotal = ((Number) matchingOrderItem.get("subtotal")).doubleValue();
            assertThat(orderSubtotal)
                .as("Subtotal should match for: " + productId)
                .isEqualTo(cartSubtotal);
        }
    }

    @Test
    @DisplayName("BONUS: Multiple orders for same user - each should be independent")
    public void testMultipleOrdersForSameUser() {
        String userId = generateUserId();
        
        // Create first order
        executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-001", 1));
        Response order1Response = executeGraphQL(
            GraphQLQueries.createOrder(userId, "Address 1"));
        String order1Id = order1Response.jsonPath().getString("data.createOrder.id");
        
        // Create second order (cart should be empty or reset)
        executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-002", 2));
        Response order2Response = executeGraphQL(
            GraphQLQueries.createOrder(userId, "Address 2"));
        String order2Id = order2Response.jsonPath().getString("data.createOrder.id");
        
        // Orders should have different IDs
        assertThat(order1Id)
            .as("Each order should have unique ID")
            .isNotEqualTo(order2Id);
        
        // Both orders should still be retrievable
        Response getOrder1 = executeGraphQL(GraphQLQueries.getOrder(order1Id));
        Response getOrder2 = executeGraphQL(GraphQLQueries.getOrder(order2Id));
        
        assertNoErrors(getOrder1);
        assertNoErrors(getOrder2);
        
        assertThat(getOrder1.jsonPath().getMap("data.order")).isNotNull();
        assertThat(getOrder2.jsonPath().getMap("data.order")).isNotNull();
    }
}
