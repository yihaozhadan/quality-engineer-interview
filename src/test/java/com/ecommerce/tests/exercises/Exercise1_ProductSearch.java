package com.ecommerce.tests.exercises;

import com.ecommerce.api.models.Product;
import com.ecommerce.api.queries.GraphQLQueries;
import com.ecommerce.tests.BaseGraphQLTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

/**
 * EXERCISE 1: Product Search Testing (15 minutes)
 * 
 * Scenario: Test the product search functionality of the eCommerce GraphQL API
 * 
 * TASKS:
 * 1. Implement test to search products by category "Electronics"
 *    - Verify response status is 200
 *    - Verify no GraphQL errors
 *    - Verify at least 1 product is returned
 *    - Verify all products have category "Electronics"
 * 
 * 2. Implement test to get a specific product by ID
 *    - Use product ID: "PROD-001"
 *    - Verify the product details are returned correctly
 *    - Verify all required fields are present (id, name, price, stock)
 * 
 * 3. Implement test for invalid product ID
 *    - Use product ID: "INVALID-ID"
 *    - Verify response handling (either returns null or error)
 *    - Handle the response appropriately
 * 
 * HINTS:
 * - Use GraphQLQueries helper class for query templates
 * - Use extractData() method from BaseGraphQLTest
 * - Response path for products list: "data.products"
 * - Response path for single product: "data.product"
 * - Use assertThat() from AssertJ for fluent assertions
 */
public class Exercise1_ProductSearch extends BaseGraphQLTest {

    @Test
    public void testSearchProductsByCategory() {
        // TODO: Implement this test
        // Step 1: Execute GraphQL query to search products by category "Electronics"
        
        // Step 2: Verify response status code is 200
        
        // Step 3: Verify no GraphQL errors in response
        
        // Step 4: Extract products list from response
        
        // Step 5: Assert that at least one product is returned
        
        // Step 6: Verify all products have category "Electronics"
        
        fail("Test not implemented yet");
    }

    @Test
    public void testGetProductById() {
        // TODO: Implement this test
        // Step 1: Execute GraphQL query to get product with ID "PROD-001"
        
        // Step 2: Verify response status code
        
        // Step 3: Extract product from response
        
        // Step 4: Assert product is not null
        
        // Step 5: Verify product fields (id, name, price, stock are not null)
        
        fail("Test not implemented yet");
    }

    @Test
    public void testGetProductById_InvalidId() {
        // TODO: Implement this test
        // Step 1: Execute GraphQL query with invalid product ID "INVALID-ID"
        
        // Step 2: Verify response (check if product is null or errors are present)
        
        // Step 3: Assert appropriate behavior for invalid ID
        
        fail("Test not implemented yet");
    }

    // BONUS: Implement a test for searching products with price filters
    // Use GraphQLQueries.searchProductsWithFilters()
    @Test
    public void testSearchProductsWithPriceFilter() {
        // TODO: Optional bonus implementation
        // Search Electronics with price range $100-$500
        
    }
}
