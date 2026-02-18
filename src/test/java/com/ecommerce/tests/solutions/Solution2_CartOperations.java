package com.ecommerce.tests.solutions;

import com.ecommerce.api.models.Cart;
import com.ecommerce.api.queries.GraphQLQueries;
import com.ecommerce.tests.BaseGraphQLTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

/**
 * SOLUTION FOR EXERCISE 2: Cart Operations Testing
 * 
 * This is a reference solution for the interviewer.
 * 
 * Key evaluation points:
 * - Proper test data isolation using unique user IDs
 * - Chaining multiple API calls to build state
 * - Validating calculated fields (totalPrice, subtotal)
 * - Testing mutations (add, update, remove)
 * - Edge case handling (invalid products, validation)
 * - Clean test structure and setup/teardown
 */
public class Solution2_CartOperations extends BaseGraphQLTest {

    @Test
    @DisplayName("Add item to cart - should create cart with correct items and total price")
    public void testAddItemToCart() {
        // Step 1: Generate a unique user ID for test isolation
        String userId = generateUserId();

        // Step 2: Execute mutation to add product "PROD-001" with quantity 2
        String mutation = GraphQLQueries.addToCart(userId, "PROD-001", 2);
        Response response = executeGraphQL(mutation);

        // Step 3: Verify response status and no errors
        assertStatusCode(response, 200);
        assertNoErrors(response);

        // Step 4: Extract cart from response
        Cart cart = response.jsonPath().getObject("data.addToCart", Cart.class);

        // Step 5: Assert cart is not null and has items
        assertThat(cart)
            .as("Cart should be created")
            .isNotNull();
        
        assertThat(cart.getUserId())
            .as("Cart should belong to correct user")
            .isEqualTo(userId);

        assertThat(cart.getItems())
            .as("Cart should have items")
            .isNotNull()
            .isNotEmpty()
            .hasSize(1);

        // Step 6: Verify the product was added with correct quantity
        Cart.CartItem item = cart.getItems().get(0);
        assertThat(item.getProductId()).isEqualTo("PROD-001");
        assertThat(item.getQuantity()).isEqualTo(2);
        assertThat(item.getPrice()).isNotNull().isPositive();

        // Step 7: Verify totalPrice is calculated correctly (should be price * quantity)
        Double expectedSubtotal = item.getPrice() * item.getQuantity();
        assertThat(item.getSubtotal())
            .as("Subtotal should equal price * quantity")
            .isEqualTo(expectedSubtotal);
        
        assertThat(cart.getTotalPrice())
            .as("Total price should equal sum of all subtotals")
            .isEqualTo(expectedSubtotal);
    }

    @Test
    @DisplayName("Update cart item quantity - should recalculate totals correctly")
    public void testUpdateCartItemQuantity() {
        // Step 1: Setup - Add a product to cart first
        String userId = generateUserId();
        String addMutation = GraphQLQueries.addToCart(userId, "PROD-001", 2);
        Response addResponse = executeGraphQL(addMutation);
        
        assertStatusCode(addResponse, 200);
        assertNoErrors(addResponse);
        
        // Get the original price for validation
        Cart originalCart = addResponse.jsonPath().getObject("data.addToCart", Cart.class);
        Double pricePerUnit = originalCart.getItems().get(0).getPrice();

        // Step 2: Update the cart item quantity to 5
        String updateMutation = GraphQLQueries.updateCartItem(userId, "PROD-001", 5);
        Response updateResponse = executeGraphQL(updateMutation);

        assertStatusCode(updateResponse, 200);
        assertNoErrors(updateResponse);

        // Step 3: Verify the quantity is updated
        Cart updatedCart = updateResponse.jsonPath().getObject("data.updateCartItem", Cart.class);
        
        assertThat(updatedCart.getItems())
            .as("Cart should still have the item")
            .hasSize(1);

        Cart.CartItem updatedItem = updatedCart.getItems().get(0);
        assertThat(updatedItem.getQuantity())
            .as("Quantity should be updated to 5")
            .isEqualTo(5);

        // Step 4: Verify totalPrice is recalculated correctly
        Double expectedSubtotal = pricePerUnit * 5;
        assertThat(updatedItem.getSubtotal())
            .as("Subtotal should be recalculated")
            .isEqualTo(expectedSubtotal);
        
        assertThat(updatedCart.getTotalPrice())
            .as("Total price should be recalculated")
            .isEqualTo(expectedSubtotal);
    }

    @Test
    @DisplayName("Remove item from cart - should remove item and update total")
    public void testRemoveItemFromCart() {
        // Step 1: Setup - Add 2 different products to cart
        String userId = generateUserId();
        
        // Add first product
        String addMutation1 = GraphQLQueries.addToCart(userId, "PROD-001", 2);
        Response addResponse1 = executeGraphQL(addMutation1);
        assertStatusCode(addResponse1, 200);
        
        // Add second product
        String addMutation2 = GraphQLQueries.addToCart(userId, "PROD-002", 1);
        Response addResponse2 = executeGraphQL(addMutation2);
        assertStatusCode(addResponse2, 200);
        
        // Verify we have 2 items
        Cart cartWith2Items = addResponse2.jsonPath().getObject("data.addToCart", Cart.class);
        assertThat(cartWith2Items.getItems()).hasSize(2);
        
        // Get the expected total after removal (just PROD-001's subtotal)
        Double expectedTotalAfterRemoval = cartWith2Items.getItems().stream()
            .filter(item -> "PROD-001".equals(item.getProductId()))
            .findFirst()
            .map(Cart.CartItem::getSubtotal)
            .orElse(0.0);

        // Step 2: Remove one product from cart (PROD-002)
        String removeMutation = GraphQLQueries.removeFromCart(userId, "PROD-002");
        Response removeResponse = executeGraphQL(removeMutation);
        
        assertStatusCode(removeResponse, 200);
        assertNoErrors(removeResponse);

        Cart updatedCart = removeResponse.jsonPath().getObject("data.removeFromCart", Cart.class);

        // Step 3: Verify the removed product is no longer in cart
        List<String> remainingProductIds = updatedCart.getItems().stream()
            .map(Cart.CartItem::getProductId)
            .toList();
        
        assertThat(remainingProductIds)
            .as("Removed product should not be in cart")
            .doesNotContain("PROD-002");

        // Step 4: Verify the other product is still in cart
        assertThat(remainingProductIds)
            .as("Other product should still be in cart")
            .contains("PROD-001");
        
        assertThat(updatedCart.getItems())
            .as("Cart should have only 1 item now")
            .hasSize(1);

        // Step 5: Verify totalPrice is updated correctly
        assertThat(updatedCart.getTotalPrice())
            .as("Total price should exclude removed item")
            .isEqualTo(expectedTotalAfterRemoval);
    }

    @Test
    @DisplayName("Add invalid product to cart - should return error")
    public void testAddInvalidProductToCart() {
        // Step 1: Try to add invalid product "INVALID-PROD"
        String userId = generateUserId();
        String mutation = GraphQLQueries.addToCart(userId, "INVALID-PROD", 1);
        Response response = executeGraphQL(mutation);

        // Step 2: Verify error handling (should have errors or null cart)
        assertStatusCode(response, 200); // GraphQL still returns 200
        
        // Either cart is null OR there are errors in the response
        Cart cart = response.jsonPath().getObject("data.addToCart", Cart.class);
        
        if (cart == null) {
            assertThat(cart)
                .as("Cart should be null for invalid product")
                .isNull();
            
            // And there should be errors
            assertThat(hasErrors(response))
                .as("Response should contain errors for invalid product")
                .isTrue();
        } else {
            // If cart somehow exists, there must be errors
            assertThat(hasErrors(response))
                .as("Response must contain errors for invalid product")
                .isTrue();
        }
    }

    @Test
    @DisplayName("BONUS: Add item with zero quantity - should validate and return error")
    public void testAddItemWithZeroQuantity() {
        String userId = generateUserId();
        String mutation = GraphQLQueries.addToCart(userId, "PROD-001", 0);
        Response response = executeGraphQL(mutation);

        // Should return error for invalid quantity
        assertStatusCode(response, 200);
        
        // Either null cart or errors should be present
        boolean hasValidationError = hasErrors(response) || 
            response.jsonPath().getObject("data.addToCart", Cart.class) == null;
        
        assertThat(hasValidationError)
            .as("Should have validation error for zero quantity")
            .isTrue();
    }

    @Test
    @DisplayName("BONUS: Add item with negative quantity - should validate and return error")
    public void testAddItemWithNegativeQuantity() {
        String userId = generateUserId();
        String mutation = GraphQLQueries.addToCart(userId, "PROD-001", -5);
        Response response = executeGraphQL(mutation);

        // Should return error for negative quantity
        assertStatusCode(response, 200);
        
        boolean hasValidationError = hasErrors(response) || 
            response.jsonPath().getObject("data.addToCart", Cart.class) == null;
        
        assertThat(hasValidationError)
            .as("Should have validation error for negative quantity")
            .isTrue();
    }

    @Test
    @DisplayName("BONUS: Verify cart total calculation with multiple items")
    public void testCartTotalWithMultipleItems() {
        String userId = generateUserId();
        
        // Add multiple products
        executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-001", 2));
        executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-002", 3));
        Response response = executeGraphQL(GraphQLQueries.addToCart(userId, "PROD-003", 1));
        
        Cart cart = response.jsonPath().getObject("data.addToCart", Cart.class);
        
        // Calculate expected total manually
        Double calculatedTotal = cart.getItems().stream()
            .mapToDouble(Cart.CartItem::getSubtotal)
            .sum();
        
        assertThat(cart.getTotalPrice())
            .as("Total price should equal sum of all subtotals")
            .isEqualTo(calculatedTotal);
    }
}
