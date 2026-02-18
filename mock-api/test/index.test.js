/**
 * GraphQL Mock Service – eCommerce Test Suite
 *
 * These Mocha tests exercise the in-memory GraphQL mock service defined in
 * mockService.js.  No network connection is required; all queries are executed
 * locally via graphql-js.
 *
 * Interview instructions
 * ──────────────────────
 * Each section below contains at least one complete, working example followed
 * by one or more TODO items for the candidate to implement.
 *
 * Goals being evaluated:
 *   1. Ability to write GraphQL queries and mutations
 *   2. Understanding of assertions with Chai (expect style)
 *   3. Handling of variables, nested fields, and error cases
 *   4. Test organisation with describe/it blocks and lifecycle hooks
 *
 * Run the suite:
 *   npm install   (first time only)
 *   npm test
 */

const { expect }              = require('chai');
const { execute, resetStore } = require('../mockService');

// ─────────────────────────────────────────────────────────────────────────────
// 1. Product Queries
// ─────────────────────────────────────────────────────────────────────────────

describe('Product Queries', () => {

    // ── Example ──────────────────────────────────────────────────────────────
    it('returns all products', async () => {
        const query = `
            query {
                products {
                    id
                    name
                    price
                }
            }
        `;
        const result = await execute(query);

        expect(result.errors).to.be.undefined;
        expect(result.data.products).to.be.an('array').that.is.not.empty;
    });

    // ── Example ──────────────────────────────────────────────────────────────
    it('returns a single product by ID with its category', async () => {
        const query = `
            query GetProduct($id: ID!) {
                product(id: $id) {
                    id
                    name
                    price
                    stock
                    category {
                        id
                        name
                    }
                }
            }
        `;
        const result = await execute(query, { id: 'prod-1' });

        expect(result.errors).to.be.undefined;
        const product = result.data.product;
        expect(product).to.not.be.null;
        expect(product.name).to.equal('Wireless Headphones');
        expect(product.price).to.equal(99.99);
        expect(product.category.name).to.equal('Electronics');
    });

    // ── TODO 1 ───────────────────────────────────────────────────────────────
    // Write a test that queries products filtered by categoryId 'cat-1'
    // (Electronics) and asserts that:
    //   - The result contains more than one product
    //   - Every returned product belongs to the 'Electronics' category
    //
    // Hint: the `products` query accepts an optional `categoryId` argument.
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('returns products filtered by category', async () => {
        // TODO: implement this test
    });

    // ── TODO 2 ───────────────────────────────────────────────────────────────
    // Write a test that queries a product with a non-existent ID (e.g. 'prod-999')
    // and asserts that:
    //   - There are no GraphQL errors
    //   - The returned product field is null
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('returns null for a non-existent product ID', async () => {
        // TODO: implement this test
    });

    // ── TODO 3 ───────────────────────────────────────────────────────────────
    // Write a test that verifies the 'Clean Code' book (prod-4) has a stock of 0.
    // Assert that the product exists and its stock value equals 0.
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('correctly reports a product with zero stock', async () => {
        // TODO: implement this test
    });
});

// ─────────────────────────────────────────────────────────────────────────────
// 2. Category Queries
// ─────────────────────────────────────────────────────────────────────────────

describe('Category Queries', () => {

    // ── Example ──────────────────────────────────────────────────────────────
    it('returns all categories', async () => {
        const query = `
            query {
                categories {
                    id
                    name
                }
            }
        `;
        const result = await execute(query);

        expect(result.errors).to.be.undefined;
        const names = result.data.categories.map(c => c.name);
        expect(names).to.include.members(['Electronics', 'Clothing', 'Books']);
    });

    // ── TODO 4 ───────────────────────────────────────────────────────────────
    // Write a test that queries the 'Electronics' category (id: 'cat-1') and
    // asserts that:
    //   - The category name is 'Electronics'
    //   - The nested `products` array contains at least 3 items
    //   - Each product has a non-null name and a price greater than 0
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('returns a category with its nested products', async () => {
        // TODO: implement this test
    });

    // ── TODO 5 ───────────────────────────────────────────────────────────────
    // Write a test that queries a category with a non-existent ID
    // and asserts that the returned value is null (no errors expected).
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('returns null for a non-existent category ID', async () => {
        // TODO: implement this test
    });
});

// ─────────────────────────────────────────────────────────────────────────────
// 3. User & Order Queries
// ─────────────────────────────────────────────────────────────────────────────

describe('User & Order Queries', () => {

    // ── Example ──────────────────────────────────────────────────────────────
    it('returns a user with their orders', async () => {
        const query = `
            query GetUser($id: ID!) {
                user(id: $id) {
                    id
                    name
                    email
                    orders {
                        id
                        status
                        total
                    }
                }
            }
        `;
        const result = await execute(query, { id: 'user-1' });

        expect(result.errors).to.be.undefined;
        const user = result.data.user;
        expect(user.name).to.equal('Alice Johnson');
        expect(user.orders).to.be.an('array').with.lengthOf(1);
        expect(user.orders[0].status).to.equal('DELIVERED');
    });

    // ── Example ──────────────────────────────────────────────────────────────
    it('returns a single order with its line items', async () => {
        const query = `
            query GetOrder($id: ID!) {
                order(id: $id) {
                    id
                    status
                    total
                    items {
                        quantity
                        unitPrice
                        product {
                            name
                        }
                    }
                }
            }
        `;
        const result = await execute(query, { id: 'order-1' });

        expect(result.errors).to.be.undefined;
        const order = result.data.order;
        expect(order.status).to.equal('DELIVERED');
        expect(order.total).to.equal(229.98);
        expect(order.items).to.have.lengthOf(2);
        expect(order.items[0].product.name).to.equal('Wireless Headphones');
    });

    // ── TODO 6 ───────────────────────────────────────────────────────────────
    // Write a test that uses the `ordersByUser` query to fetch all orders for
    // user-2 (Bob Smith) and asserts that:
    //   - Exactly 1 order is returned
    //   - That order has status 'PENDING'
    //   - The order total equals 19.99
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('returns all orders for a specific user', async () => {
        // TODO: implement this test
    });

    // ── TODO 7 ───────────────────────────────────────────────────────────────
    // Write a test that queries a user with a non-existent ID and asserts that
    // the returned user is null (no GraphQL errors expected).
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('returns null for a non-existent user ID', async () => {
        // TODO: implement this test
    });
});

// ─────────────────────────────────────────────────────────────────────────────
// 4. Mutations
// ─────────────────────────────────────────────────────────────────────────────

describe('Mutations', () => {

    // Reset the in-memory store before each test in this suite so that
    // mutations do not bleed into one another.
    beforeEach(() => resetStore());

    // ── Example ──────────────────────────────────────────────────────────────
    it('places a new order and returns it with PENDING status', async () => {
        const mutation = `
            mutation PlaceOrder($userId: ID!, $items: [OrderItemInput!]!) {
                placeOrder(userId: $userId, items: $items) {
                    id
                    status
                    total
                    items {
                        quantity
                        unitPrice
                        product {
                            name
                        }
                    }
                }
            }
        `;
        const variables = {
            userId: 'user-1',
            items: [
                { productId: 'prod-5', quantity: 2 },
            ],
        };
        const result = await execute(mutation, variables);

        expect(result.errors).to.be.undefined;
        const order = result.data.placeOrder;
        expect(order.status).to.equal('PENDING');
        expect(order.total).to.equal(99.98);          // 49.99 × 2
        expect(order.items).to.have.lengthOf(1);
        expect(order.items[0].product.name).to.equal('USB-C Hub');
    });

    // ── TODO 8 ───────────────────────────────────────────────────────────────
    // Write a test that:
    //   1. Places a new order for user-2 containing two different products
    //   2. Asserts the returned order has status 'PENDING'
    //   3. Asserts the total is the sum of (price × quantity) for each item
    //   4. Asserts the order contains exactly 2 items
    //
    // Hint: use the seed prices in data/products.js to calculate the expected total.
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('places an order with multiple items and calculates the correct total', async () => {
        // TODO: implement this test
    });

    // ── TODO 9 ───────────────────────────────────────────────────────────────
    // Write a test that updates the status of order-2 from PENDING to SHIPPED
    // using the `updateOrderStatus` mutation and asserts that:
    //   - No errors are returned
    //   - The returned order id is 'order-2'
    //   - The returned status is 'SHIPPED'
    //
    // Hint: the mutation signature is:
    //   updateOrderStatus(orderId: ID!, status: OrderStatus!): Order!
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('updates an order status from PENDING to SHIPPED', async () => {
        // TODO: implement this test
    });

    // ── TODO 10 ──────────────────────────────────────────────────────────────
    // Write a test that attempts to place an order for a non-existent user
    // (e.g. userId: 'user-999') and asserts that:
    //   - The result contains a non-empty `errors` array
    //   - The error message includes the text 'not found'
    //
    // Hint: the mock service throws an Error when the user is not found.
    //       graphql-js catches thrown errors and surfaces them in result.errors.
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('returns an error when placing an order for a non-existent user', async () => {
        // TODO: implement this test
    });
});

// ─────────────────────────────────────────────────────────────────────────────
// 5. Advanced / Bonus Challenges
// ─────────────────────────────────────────────────────────────────────────────

describe('Advanced Challenges (Bonus)', () => {

    beforeEach(() => resetStore());

    // ── TODO 11 ──────────────────────────────────────────────────────────────
    // Write a test that verifies state persistence across two mutations in the
    // same test:
    //   1. Place a new order for any user.
    //   2. Immediately query that order by the ID returned from the mutation.
    //   3. Assert the queried order matches the one returned by the mutation.
    //
    // This tests that the mock store correctly retains newly created records.
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('persists a newly placed order so it can be retrieved by ID', async () => {
        // TODO: implement this test
    });

    // ── TODO 12 ──────────────────────────────────────────────────────────────
    // Write a test that uses a single GraphQL query to fetch BOTH a product AND
    // a category in one round-trip by using query aliases:
    //
    //   query {
    //     headphones: product(id: "prod-1") { ... }
    //     electronics: category(id: "cat-1") { ... }
    //   }
    //
    // Assert that both aliases return the expected data.
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('fetches multiple resources in a single query using aliases', async () => {
        // TODO: implement this test
    });

    // ── TODO 13 ──────────────────────────────────────────────────────────────
    // Write a parameterised test that iterates over the following product IDs
    // and asserts that each one exists and has a price greater than 0:
    //   ['prod-1', 'prod-2', 'prod-3', 'prod-5']
    //
    // Hint: you can generate it() blocks dynamically inside a describe() using
    //       a forEach loop, or use a single it() that loops and asserts.
    // ─────────────────────────────────────────────────────────────────────────
    it.skip('all in-stock products have a positive price', async () => {
        // TODO: implement this test
    });
});
