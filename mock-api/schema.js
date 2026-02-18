/**
 * eCommerce GraphQL Schema
 *
 * Defines the types, queries, and mutations available in the mock service.
 * The schema covers the core entities of a simple online store:
 *   - Product    – an item for sale
 *   - Category   – product grouping
 *   - User       – a registered customer
 *   - Order      – a purchase made by a user
 *   - OrderItem  – a single line inside an order
 */

const { buildSchema } = require('graphql');

const schema = buildSchema(`
    # ─── Enums ───────────────────────────────────────────────────────────────────

    enum OrderStatus {
        PENDING
        PROCESSING
        SHIPPED
        DELIVERED
        CANCELLED
    }

    # ─── Types ────────────────────────────────────────────────────────────────────

    type Category {
        id: ID!
        name: String!
        description: String
        products: [Product!]!
    }

    type Product {
        id: ID!
        name: String!
        description: String
        price: Float!
        stock: Int!
        category: Category!
    }

    type User {
        id: ID!
        name: String!
        email: String!
        orders: [Order!]!
    }

    type OrderItem {
        product: Product!
        quantity: Int!
        unitPrice: Float!
    }

    type Order {
        id: ID!
        user: User!
        status: OrderStatus!
        total: Float!
        items: [OrderItem!]!
    }

    # ─── Input Types ──────────────────────────────────────────────────────────────

    input OrderItemInput {
        productId: ID!
        quantity: Int!
    }

    # ─── Queries ──────────────────────────────────────────────────────────────────

    type Query {
        # Retrieve all products, optionally filtered by category
        products(categoryId: ID): [Product!]!

        # Retrieve a single product by ID
        product(id: ID!): Product

        # Retrieve all categories
        categories: [Category!]!

        # Retrieve a single category by ID
        category(id: ID!): Category

        # Retrieve a single user by ID
        user(id: ID!): User

        # Retrieve a single order by ID
        order(id: ID!): Order

        # Retrieve all orders for a given user
        ordersByUser(userId: ID!): [Order!]!
    }

    # ─── Mutations ────────────────────────────────────────────────────────────────

    type Mutation {
        # Place a new order for a user
        placeOrder(userId: ID!, items: [OrderItemInput!]!): Order!

        # Update the status of an existing order
        updateOrderStatus(orderId: ID!, status: OrderStatus!): Order!
    }
`);

module.exports = { schema };
