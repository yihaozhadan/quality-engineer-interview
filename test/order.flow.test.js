const chai = require('chai');
const chaiHttp = require('chai-http');
const expect = chai.expect;

// Use chai-http plugin for making HTTP requests
chai.use(chaiHttp);

// Base URL for the API server
const BASE_URL = 'http://localhost:3000';

// =============================================================================
// E-COMMERCE ORDER FLOW INTEGRATION TESTS
// =============================================================================
//
// CANDIDATE INSTRUCTIONS:
//   Complete all test cases marked with "// TODO" below.
//
//   Requirements:
//   1. Write at least 8 test cases covering the happy path and edge cases
//   2. Use proper Mocha hooks (before, after) for setup and cleanup
//   3. Assert on HTTP status codes and response body structure
//   4. Handle async operations correctly with async/await
//   5. Test error scenarios (invalid inputs, missing resources, failed payments)
//
//   Time Allowed: 45 minutes
//
// =============================================================================

describe('E-Commerce Order Flow', () => {
  // Variables to store IDs created during setup
  let customer;
  let cart;
  let payment;
  let order;

  // ---------------------------------------------------------------------------
  // SETUP: Create a customer and cart before running tests
  // ---------------------------------------------------------------------------
  before(async () => {
    // TODO: Create a test customer using POST /api/customers
    // Hint: Use chai.request(BASE_URL).post('/api/customers').send(...)
    // Store the response in the `customer` variable

    // TODO: Create a cart for the customer using POST /api/carts
    // Hint: Send { customerId: customer.id }
    // Store the response in the `cart` variable
  });

  // ---------------------------------------------------------------------------
  // CLEANUP: Delete resources after tests complete
  // ---------------------------------------------------------------------------
  after(async () => {
    // TODO: Clean up the cart if it exists using DELETE /api/carts/:id
    // This ensures test isolation for future test runs
  });

  // ---------------------------------------------------------------------------
  // MENU SERVICE TESTS
  // ---------------------------------------------------------------------------
  describe('Menu Service', () => {
    it('should return all menu items', async () => {
      // TODO: Make a GET request to /api/menu
      // Assert that the response status is 200
      // Assert that the response body is an array with at least 6 items
    });

    it('should return a specific menu item by ID', async () => {
      // TODO: Make a GET request to /api/menu/1
      // Assert that the response status is 200
      // Assert that the menu item has the expected name "Classic Burger"
      // Assert that the price is 8.99
    });

    it('should return 404 for non-existent menu item', async () => {
      // TODO: Make a GET request to /api/menu/999
      // Assert that the response status is 404
      // Assert that the response body contains an error message
    });
  });

  // ---------------------------------------------------------------------------
  // CUSTOMER SERVICE TESTS
  // ---------------------------------------------------------------------------
  describe('Customer Service', () => {
    it('should return the created customer by ID', async () => {
      // TODO: Make a GET request to /api/customers/:id using the customer created in `before`
      // Assert that the response status is 200
      // Assert that the customer name and email match what was created
    });

    it('should return 404 for non-existent customer', async () => {
      // TODO: Make a GET request to /api/customers/cust_nonexistent
      // Assert that the response status is 404
    });

    it('should fail to create a customer without required fields', async () => {
      // TODO: Make a POST request to /api/customers with an empty body or missing name/email
      // Assert that the response status is 422
      // Assert that the response body contains an error message
    });
  });

  // ---------------------------------------------------------------------------
  // CART SERVICE TESTS
  // ---------------------------------------------------------------------------
  describe('Cart Service', () => {
    it('should add an item to the cart', async () => {
      // TODO: Make a POST request to /api/carts/:id/items
      // Add menu item ID 1 (Classic Burger) with quantity 2
      // Assert that the response status is 200
      // Assert that the cart now has 1 item
      // Assert that the total is calculated correctly (8.99 * 2 = 17.98)
    });

    it('should fail to add an item with invalid quantity', async () => {
      // TODO: Try to add an item with quantity 0 or negative
      // Assert that the response status is 422
    });

    it('should fail to add a non-existent menu item to cart', async () => {
      // TODO: Try to add menu item ID 999 (does not exist)
      // Assert that the response status is 404
    });

    it('should fail to create a cart for non-existent customer', async () => {
      // TODO: Make a POST request to /api/carts with a fake customer ID
      // Assert that the response status is 404
    });
  });

  // ---------------------------------------------------------------------------
  // PAYMENT SERVICE TESTS
  // ---------------------------------------------------------------------------
  describe('Payment Service', () => {
    it('should process a payment with valid details', async () => {
      // TODO: Make a POST request to /api/payments
      // Send customerId, amount (e.g., 17.98), and paymentMethod "credit_card"
      // Assert that the response status is 201
      // Assert that the payment status is "approved"
      // Store the payment response for use in order creation
    });

    it('should fail to process payment with invalid payment method', async () => {
      // TODO: Try to process a payment with an invalid method (e.g., "crypto")
      // Assert that the response status is 422
    });

    it('should fail to process payment with negative amount', async () => {
      // TODO: Try to process a payment with amount <= 0
      // Assert that the response status is 422
    });
  });

  // ---------------------------------------------------------------------------
  // ORDER SERVICE TESTS - Full Order Flow
  // ---------------------------------------------------------------------------
  describe('Order Service - Full Flow', () => {
    it('should place an order with valid cart and payment', async () => {
      // TODO: This test simulates the complete order flow:
      //   1. First, add items to the cart (POST /api/carts/:id/items)
      //   2. Get the cart total (GET /api/carts/:id)
      //   3. Process payment for the cart total (POST /api/payments)
      //   4. Place the order with customerId, cartId, and paymentId (POST /api/orders)
      //   5. Assert the order status is "confirmed"
      //   6. Assert the order total matches the cart total
      //   7. Assert the order contains the correct items
    });

    it('should fail to place an order with empty cart', async () => {
      // TODO: Create a new empty cart, process a payment, then try to place an order
      // Assert that the response status is 422
    });

    it('should fail to place an order with mismatched customer/cart', async () => {
      // TODO: Create a new customer, create a cart for the first customer,
      // then try to place an order using the second customer's ID with the first customer's cart
      // Assert that the response status is 422
    });

    it('should update order status from confirmed to preparing', async () => {
      // TODO: Use the order created in the previous test
      // Make a PUT request to /api/orders/:id with { status: 'preparing' }
      // Assert that the response status is 200
      // Assert that the order status is now "preparing"
    });

    it('should fail to skip order statuses', async () => {
      // TODO: Try to update an order from "preparing" directly to "delivered" (skipping "ready")
      // Assert that the response status is 422
    });
  });
});