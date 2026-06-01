# E-Commerce Order Flow – Mocha Test Interview

## Interview Overview

This interview assesses a candidate's ability to write integration tests using **Mocha**, **Chai**, and **Chai-HTTP** against a simulated e-commerce REST API. The candidate will write tests that cover the full order lifecycle: browsing menu items, managing a cart, processing payment, and placing orders.

---

## Evaluation Criteria

| Skill Area | What We're Looking For |
|---|---|
| **Mocha Testing** | Proper use of `describe`, `it`, `before`, `after`, `async/await`, and test isolation |
| **HTTP Assertions** | Correct status code checks, response body validation, and error handling |
| **Node.js Basics** | Understanding of `require`, `module.exports`, async patterns, and error handling |
| **Test Quality** | Clear test descriptions, proper setup/teardown, and edge case coverage |
| **Code Organization** | Logical structure, reusable helpers, and readable code |

---

## REST API Endpoints

The API server runs on `http://localhost:3000`. Below are the available endpoints:

### Customer Service

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/customers` | Create a new customer. Body: `{ name, email, phone }` |
| `GET` | `/api/customers/:id` | Get customer by ID |
| `GET` | `/api/customers` | List all customers |

### Menu Service

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/menu` | List all menu items |
| `GET` | `/api/menu/:id` | Get a specific menu item |

### Cart Service

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/carts` | Create a new cart for a customer. Body: `{ customerId }` |
| `GET` | `/api/carts/:id` | Get cart by ID |
| `POST` | `/api/carts/:id/items` | Add item to cart. Body: `{ menuItemId, quantity }` |
| `PUT` | `/api/carts/:id/items/:itemIndex` | Update cart item quantity. Body: `{ quantity }` |
| `DELETE` | `/api/carts/:id/items/:itemIndex` | Remove item from cart |
| `DELETE` | `/api/carts/:id` | Delete/empty the cart |

### Payment Service

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/payments` | Process a payment. Body: `{ customerId, amount, paymentMethod }` |
| `GET` | `/api/payments/:id` | Get payment status |

### Order Service

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/orders` | Place an order. Body: `{ customerId, cartId, paymentId }` |
| `GET` | `/api/orders/:id` | Get order by ID |
| `GET` | `/api/orders` | List all orders |
| `PUT` | `/api/orders/:id` | Update order status. Body: `{ status }` |

---

## Sample Data

### Menu Items (pre-loaded)

| ID | Name | Price | Category |
|---|---|---|---|
| 1 | Classic Burger | 8.99 | Burgers |
| 2 | Cheese Burger | 9.99 | Burgers |
| 3 | Chicken Nuggets | 5.49 | Sides |
| 4 | French Fries | 3.99 | Sides |
| 5 | Cola | 2.49 | Drinks |
| 6 | Lemonade | 2.99 | Drinks |

### Valid Payment Methods
- `credit_card`
- `debit_card`
- `cash`

### Order Statuses
- `pending` → `confirmed` → `preparing` → `ready` → `delivered`

---

## Prerequisites

- **Node.js** v14 or higher (includes npm)
  - Download from [nodejs.org](https://nodejs.org/)
  - Verify installation: `node -v` and `npm -v`

## Running the Interview

### 1. Install Dependencies

```bash
npm install
```

> **Note:** If `npm` is not found, install Node.js from [nodejs.org](https://nodejs.org/) first.

### 2. Start the Mock API Server

```bash
npm run server
```

The server will start on `http://localhost:3000` with in-memory data.

### 3. Run Tests

```bash
npm test
```

---

## Candidate Instructions

You will find a starter test file at `test/order.flow.test.js`. Your task is to complete the test cases marked with `// TODO`.

### Requirements

1. **Write at least 8 test cases** covering the happy path and edge cases
2. **Use proper Mocha hooks** (`before`, `after`) for setup and cleanup
3. **Assert on HTTP status codes** and response body structure
4. **Handle async operations** correctly with `async/await`
5. **Test error scenarios** (invalid inputs, missing resources, failed payments)

### Time Allowed: 45 minutes

---

## Server Response Examples

### Create Customer Response (201)
```json
{
  "id": "cust_001",
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "555-0100",
  "createdAt": "2025-01-01T00:00:00.000Z"
}
```

### Menu Items Response (200)
```json
[
  { "id": 1, "name": "Classic Burger", "price": 8.99, "category": "Burgers" },
  { "id": 2, "name": "Cheese Burger", "price": 9.99, "category": "Burgers" }
]
```

### Create Cart Response (201)
```json
{
  "id": "cart_001",
  "customerId": "cust_001",
  "items": [],
  "total": 0,
  "createdAt": "2025-01-01T00:00:00.000Z"
}
```

### Process Payment Response (201)
```json
{
  "id": "pay_001",
  "customerId": "cust_001",
  "amount": 12.48,
  "paymentMethod": "credit_card",
  "status": "approved",
  "processedAt": "2025-01-01T00:00:00.000Z"
}
```

### Place Order Response (201)
```json
{
  "id": "order_001",
  "customerId": "cust_001",
  "cartId": "cart_001",
  "paymentId": "pay_001",
  "status": "confirmed",
  "total": 12.48,
  "items": [
    { "menuItemId": 1, "name": "Classic Burger", "quantity": 1, "price": 8.99 }
  ],
  "createdAt": "2025-01-01T00:00:00.000Z"
}
```

### Error Response (400/404/422)
```json
{
  "error": "Invalid request",
  "message": "Customer ID is required"
}
```

---

## Scoring Rubric

| Score | Mocha Knowledge | HTTP Assertions | Node.js Basics | Test Quality |
|---|---|---|---|---|
| **1-2: Needs Improvement** | Incorrect hook usage | Missing status checks | Callback/ promise confusion | Poor descriptions |
| **3-4: Proficient** | Correct structure | Basic assertions | Functional async/await | Clear and organized |
| **5: Excellent** | Advanced patterns | Comprehensive assertions | Clean error handling | Edge cases covered |

---

## Answer Key

Completed solutions are available in `test/solution/` for interviewer reference.