# GraphQL Mock API – eCommerce Test Suite

This module provides a self-contained GraphQL mock service backed by in-memory
seed data, together with a Mocha test suite designed for quality-engineer
interview exercises.

## Project Structure

```
mock-api/
├── data/
│   └── products.js       # Seed data: categories, products, users, orders
├── test/
│   └── index.test.js     # Mocha test suite (interview exercise)
├── mockService.js        # In-memory GraphQL execution layer
├── schema.js             # eCommerce GraphQL schema (SDL)
├── package.json
└── README.md
```

## Schema Overview

| Type        | Key Fields                                      |
|-------------|-------------------------------------------------|
| `Product`   | id, name, price, stock, category                |
| `Category`  | id, name, products                              |
| `User`      | id, name, email, orders                         |
| `Order`     | id, status, total, items, user                  |
| `OrderItem` | product, quantity, unitPrice                    |

**Queries:** `products`, `product`, `categories`, `category`, `user`, `order`, `ordersByUser`

**Mutations:** `placeOrder`, `updateOrderStatus`

## Commands

```bash
npm install   # install dependencies (first time only)
npm test      # run the Mocha test suite
```

No config file is needed; Mocha uses its default settings (`test/` directory,
`*.test.js` pattern).

## Interview Exercise

Open `test/index.test.js`.  The file contains:

- **Working examples** – complete, passing tests that demonstrate the pattern.
- **TODO items (1–13)** – skipped tests (`it.skip`) for the candidate to implement.

The candidate should replace each `it.skip` with a working `it` block and fill
in the query/assertions described in the comment above it.

### Difficulty breakdown

| TODOs   | Topic                                      |
|---------|--------------------------------------------|
| 1–3     | Product queries with arguments & null cases |
| 4–5     | Category queries with nested fields         |
| 6–7     | User / order queries                        |
| 8–10    | Mutations and error handling                |
| 11–13   | Advanced: state persistence, aliases, loops |
