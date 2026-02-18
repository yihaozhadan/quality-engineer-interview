package com.ecommerce.tests.solutions;

import com.ecommerce.api.models.Product;
import com.ecommerce.api.queries.GraphQLQueries;
import com.ecommerce.tests.BaseGraphQLTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

/**
 * SOLUTION FOR EXERCISE 1: Product Search Testing
 * 
 * This is a reference solution for the interviewer.
 * Do NOT share with candidates before the interview.
 * 
 * Key evaluation points:
 * - Proper use of helper methods from BaseGraphQLTest
 * - Complete assertions checking all relevant fields
 * - GraphQL-specific error handling (200 status with errors in response)
 * - Edge case handling (invalid IDs)
 * - Code organization and readability
 */
public class Solution1_ProductSearch extends BaseGraphQLTest {

    @Test
    @DisplayName("Search products by category 'Electronics' - should return all electronics products")
    public void testSearchProductsByCategory() {
        // Step 1: Execute GraphQL query
        String query = GraphQLQueries.searchProductsByCategory("Electronics");
        Response response = executeGraphQL(query);

        // Step 2: Verify response status code is 200
        assertStatusCode(response, 200);

        // Step 3: Verify no GraphQL errors in response
        assertNoErrors(response);

        // Step 4: Extract products list from response
        List<Product> products = response.jsonPath().getList("data.products", Product.class);

        // Step 5: Assert that at least one product is returned
        assertThat(products)
            .as("Products list should not be null or empty")
            .isNotNull()
            .isNotEmpty();

        // Step 6: Verify all products have category "Electronics"
        assertThat(products)
            .allMatch(product -> "Electronics".equals(product.getCategory()),
                     "All products should be in Electronics category");

        // Additional quality checks
        assertThat(products)
            .allMatch(product -> product.getId() != null, "Product ID should not be null")
            .allMatch(product -> product.getName() != null && !product.getName().isEmpty(), 
                     "Product name should not be null or empty")
            .allMatch(product -> product.getPrice() != null && product.getPrice() > 0, 
                     "Product price should be positive");
    }

    @Test
    @DisplayName("Get product by valid ID - should return complete product details")
    public void testGetProductById() {
        // Step 1: Execute GraphQL query to get product with ID "PROD-001"
        String query = GraphQLQueries.getProductById("PROD-001");
        Response response = executeGraphQL(query);

        // Step 2: Verify response status code
        assertStatusCode(response, 200);
        assertNoErrors(response);

        // Step 3: Extract product from response
        Product product = response.jsonPath().getObject("data.product", Product.class);

        // Step 4: Assert product is not null
        assertThat(product)
            .as("Product should be found for valid ID")
            .isNotNull();

        // Step 5: Verify product fields (id, name, price, stock are not null)
        assertThat(product.getId())
            .as("Product ID should match requested ID")
            .isEqualTo("PROD-001");
        
        assertThat(product.getName())
            .as("Product name should not be null or empty")
            .isNotNull()
            .isNotEmpty();
        
        assertThat(product.getPrice())
            .as("Product price should be positive")
            .isNotNull()
            .isPositive();
        
        assertThat(product.getStock())
            .as("Product stock should be non-negative")
            .isNotNull()
            .isGreaterThanOrEqualTo(0);

        assertThat(product.getCategory())
            .as("Product category should not be null")
            .isNotNull();
    }

    @Test
    @DisplayName("Get product with invalid ID - should handle gracefully with null or error")
    public void testGetProductById_InvalidId() {
        // Step 1: Execute GraphQL query with invalid product ID "INVALID-ID"
        String query = GraphQLQueries.getProductById("INVALID-ID");
        Response response = executeGraphQL(query);

        // Step 2: Verify response (check if product is null or errors are present)
        // Status should still be 200 for GraphQL (errors are in response body)
        assertStatusCode(response, 200);

        // Step 3: Assert appropriate behavior for invalid ID
        // Either product is null OR there are errors
        Product product = response.jsonPath().getObject("data.product", Product.class);
        
        // This is an example of proper GraphQL error handling
        // GraphQL can return 200 with null data and errors in the response
        if (product == null) {
            assertThat(product)
                .as("Product should be null for invalid ID")
                .isNull();
        } else {
            // If product is somehow returned, there should be errors
            assertThat(hasErrors(response))
                .as("Response should contain errors for invalid product ID")
                .isTrue();
        }
    }

    @Test
    @DisplayName("BONUS: Search products with price filter - should return only products in price range")
    public void testSearchProductsWithPriceFilter() {
        // Search Electronics with price range $100-$500
        String query = GraphQLQueries.searchProductsWithFilters("Electronics", 100.0, 500.0);
        Response response = executeGraphQL(query);

        // Verify response
        assertStatusCode(response, 200);
        assertNoErrors(response);

        // Extract and validate products
        List<Product> products = response.jsonPath().getList("data.products", Product.class);

        // Assert price range
        assertThat(products)
            .as("Should return products within price range")
            .isNotEmpty()
            .allMatch(p -> p.getPrice() >= 100.0 && p.getPrice() <= 500.0,
                     "All products should be within price range $100-$500");

        // Verify all are still Electronics
        assertThat(products)
            .allMatch(p -> "Electronics".equals(p.getCategory()),
                     "All products should still be in Electronics category");
    }
}
