package com.ecommerce.api.queries;

/**
 * GraphQL query templates for the eCommerce API
 */
public class GraphQLQueries {

    /**
     * Query to search products by category
     */
    public static String searchProductsByCategory(String category) {
        return String.format(
            "{ \"query\": \"{ products(category: \\\"%s\\\") { id name category price stock } }\" }",
            category
        );
    }

    /**
     * Query to get product by ID
     */
    public static String getProductById(String productId) {
        return String.format(
            "{ \"query\": \"{ product(id: \\\"%s\\\") { id name category price stock description } }\" }",
            productId
        );
    }

    /**
     * Query to get products with filters
     */
    public static String searchProductsWithFilters(String category, Double minPrice, Double maxPrice) {
        return String.format(
            "{ \"query\": \"{ products(category: \\\"%s\\\", minPrice: %.2f, maxPrice: %.2f) { id name category price stock } }\" }",
            category, minPrice, maxPrice
        );
    }

    /**
     * Mutation to add item to cart
     */
    public static String addToCart(String userId, String productId, int quantity) {
        return String.format(
            "{ \"query\": \"mutation { addToCart(userId: \\\"%s\\\", productId: \\\"%s\\\", quantity: %d) { id userId items { productId productName quantity price subtotal } totalPrice } }\" }",
            userId, productId, quantity
        );
    }

    /**
     * Query to get cart by user ID
     */
    public static String getCart(String userId) {
        return String.format(
            "{ \"query\": \"{ cart(userId: \\\"%s\\\") { id userId items { productId productName quantity price subtotal } totalPrice } }\" }",
            userId
        );
    }

    /**
     * Mutation to update cart item quantity
     */
    public static String updateCartItem(String userId, String productId, int quantity) {
        return String.format(
            "{ \"query\": \"mutation { updateCartItem(userId: \\\"%s\\\", productId: \\\"%s\\\", quantity: %d) { id userId items { productId productName quantity price subtotal } totalPrice } }\" }",
            userId, productId, quantity
        );
    }

    /**
     * Mutation to remove item from cart
     */
    public static String removeFromCart(String userId, String productId) {
        return String.format(
            "{ \"query\": \"mutation { removeFromCart(userId: \\\"%s\\\", productId: \\\"%s\\\") { id userId items { productId productName quantity price subtotal } totalPrice } }\" }",
            userId, productId
        );
    }

    /**
     * Mutation to create order from cart
     */
    public static String createOrder(String userId, String shippingAddress) {
        return String.format(
            "{ \"query\": \"mutation { createOrder(userId: \\\"%s\\\", shippingAddress: \\\"%s\\\") { id userId orderDate status items { productId productName quantity price subtotal } totalPrice shippingAddress } }\" }",
            userId, shippingAddress.replace("\"", "\\\"")
        );
    }

    /**
     * Query to get order by ID
     */
    public static String getOrder(String orderId) {
        return String.format(
            "{ \"query\": \"{ order(id: \\\"%s\\\") { id userId orderDate status items { productId productName quantity price subtotal } totalPrice shippingAddress } }\" }",
            orderId
        );
    }
}
