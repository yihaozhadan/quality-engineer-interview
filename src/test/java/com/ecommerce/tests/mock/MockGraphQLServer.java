package com.ecommerce.tests.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/**
 * OPTIONAL: Mock GraphQL server for running tests
 * 
 * This is NOT required for the interview - it's provided in case you want
 * the tests to actually execute against a mock server.
 * 
 * The interview focuses on code quality and structure, not execution.
 * 
 * To use this:
 * 1. Have your test class extend MockGraphQLServer
 * 2. Tests will run against a mock server on localhost:8080
 */
public class MockGraphQLServer {

    protected static WireMockServer wireMockServer;

    @BeforeAll
    public static void setupMockServer() {
        wireMockServer = new WireMockServer(options().port(8080));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);

        // Mock: Search products by category "Electronics"
        stubFor(post(urlEqualTo("/graphql"))
            .withRequestBody(containing("products(category: \\\"Electronics\\\")"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\n" +
                    "  \"data\": {\n" +
                    "    \"products\": [\n" +
                    "      {\n" +
                    "        \"id\": \"PROD-001\",\n" +
                    "        \"name\": \"Laptop Pro 15\",\n" +
                    "        \"category\": \"Electronics\",\n" +
                    "        \"price\": 1299.99,\n" +
                    "        \"stock\": 15\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"PROD-002\",\n" +
                    "        \"name\": \"Wireless Mouse\",\n" +
                    "        \"category\": \"Electronics\",\n" +
                    "        \"price\": 29.99,\n" +
                    "        \"stock\": 50\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}")));

        // Mock: Get product by ID "PROD-001"
        stubFor(post(urlEqualTo("/graphql"))
            .withRequestBody(containing("product(id: \\\"PROD-001\\\")"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\n" +
                    "  \"data\": {\n" +
                    "    \"product\": {\n" +
                    "      \"id\": \"PROD-001\",\n" +
                    "      \"name\": \"Laptop Pro 15\",\n" +
                    "      \"category\": \"Electronics\",\n" +
                    "      \"price\": 1299.99,\n" +
                    "      \"stock\": 15,\n" +
                    "      \"description\": \"High-performance laptop\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "}")));

        // Mock: Invalid product ID
        stubFor(post(urlEqualTo("/graphql"))
            .withRequestBody(containing("product(id: \\\"INVALID-ID\\\")"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\n" +
                    "  \"data\": {\n" +
                    "    \"product\": null\n" +
                    "  },\n" +
                    "  \"errors\": [\n" +
                    "    {\n" +
                    "      \"message\": \"Product not found\",\n" +
                    "      \"path\": [\"product\"]\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}")));

        // Mock: Add to cart
        stubFor(post(urlEqualTo("/graphql"))
            .withRequestBody(containing("addToCart"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\n" +
                    "  \"data\": {\n" +
                    "    \"addToCart\": {\n" +
                    "      \"id\": \"cart_123\",\n" +
                    "      \"userId\": \"user_test\",\n" +
                    "      \"items\": [\n" +
                    "        {\n" +
                    "          \"productId\": \"PROD-001\",\n" +
                    "          \"productName\": \"Laptop Pro 15\",\n" +
                    "          \"quantity\": 2,\n" +
                    "          \"price\": 1299.99,\n" +
                    "          \"subtotal\": 2599.98\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"totalPrice\": 2599.98\n" +
                    "    }\n" +
                    "  }\n" +
                    "}")));

        // Add more mocks as needed for other operations...
    }

    @AfterAll
    public static void teardownMockServer() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }
}
