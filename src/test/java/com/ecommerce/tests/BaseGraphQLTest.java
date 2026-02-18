package com.ecommerce.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base test class providing common setup and utility methods for GraphQL API testing
 */
public class BaseGraphQLTest {

    protected static final String BASE_URL = "http://localhost:8080";
    protected static final String GRAPHQL_ENDPOINT = "/graphql";

    @BeforeAll
    public static void setupBase() {
        RestAssured.baseURI = BASE_URL;
    }

    @BeforeEach
    public void setup() {
        // Override in subclasses if needed
    }

    /**
     * Execute a GraphQL query and return the response
     * 
     * @param graphQLQuery The GraphQL query in JSON format
     * @return Response object
     */
    protected Response executeGraphQL(String graphQLQuery) {
        return getRequestSpec()
                .body(graphQLQuery)
                .when()
                .post(GRAPHQL_ENDPOINT);
    }

    /**
     * Get a configured request specification for GraphQL
     * 
     * @return RequestSpecification with common settings
     */
    protected RequestSpecification getRequestSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
    }

    /**
     * Helper method to extract data from GraphQL response
     * 
     * @param response The API response
     * @param path JsonPath to the data (e.g., "data.products")
     * @return Extracted value
     */
    protected <T> T extractData(Response response, String path) {
        return response.jsonPath().get(path);
    }

    /**
     * Helper method to check if response has errors
     * 
     * @param response The API response
     * @return true if errors exist, false otherwise
     */
    protected boolean hasErrors(Response response) {
        return response.jsonPath().get("errors") != null;
    }

    /**
     * Helper method to get error message from response
     * 
     * @param response The API response
     * @return Error message or null
     */
    protected String getErrorMessage(Response response) {
        if (hasErrors(response)) {
            return response.jsonPath().getString("errors[0].message");
        }
        return null;
    }

    /**
     * Generate a unique test user ID
     * 
     * @return Unique user ID
     */
    protected String generateUserId() {
        return "user_" + System.currentTimeMillis();
    }

    /**
     * Common assertion: verify response status code
     */
    protected void assertStatusCode(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    /**
     * Common assertion: verify no GraphQL errors
     */
    protected void assertNoErrors(Response response) {
        if (hasErrors(response)) {
            throw new AssertionError("GraphQL response contains errors: " + getErrorMessage(response));
        }
    }
}
