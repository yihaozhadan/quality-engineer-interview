/**
 * GraphQL Mock Service
 *
 * Provides an in-memory GraphQL execution layer using the eCommerce schema.
 * No HTTP server is required – tests call `execute()` directly, which keeps
 * the suite fast and self-contained.
 *
 * Usage:
 *   const { execute } = require('../mockService');
 *   const result = await execute(query, variables);
 */

const { graphql } = require('graphql');
const { schema }  = require('./schema');
const { categories, products, users, orders } = require('./data/products');

// ─── In-memory store (mutable for mutation tests) ─────────────────────────────

// Deep-clone the seed data so each test run starts from a known state.
// Call resetStore() in a beforeEach hook when mutation tests need isolation.
let store = buildStore();

function buildStore() {
    return {
        categories: categories.map(c => ({ ...c })),
        products:   products.map(p => ({ ...p })),
        users:      users.map(u => ({ ...u })),
        orders:     orders.map(o => ({
            ...o,
            items: o.items.map(i => ({ ...i })),
        })),
        nextOrderId: orders.length + 1,
    };
}

/** Reset the in-memory store to its original seed state. */
function resetStore() {
    store = buildStore();
}

// ─── Resolver helpers ─────────────────────────────────────────────────────────

function findById(collection, id) {
    return collection.find(item => item.id === id) || null;
}

function resolveProduct(p) {
    return {
        ...p,
        category: () => findById(store.categories, p.categoryId),
    };
}

function resolveOrder(o) {
    return {
        ...o,
        user:  () => resolveUser(findById(store.users, o.userId)),
        items: () => o.items.map(item => ({
            quantity:  item.quantity,
            unitPrice: item.unitPrice,
            product:   () => resolveProduct(findById(store.products, item.productId)),
        })),
    };
}

function resolveUser(u) {
    if (!u) return null;
    return {
        ...u,
        orders: () => store.orders
            .filter(o => o.userId === u.id)
            .map(resolveOrder),
    };
}

function resolveCategory(c) {
    return {
        ...c,
        products: () => store.products
            .filter(p => p.categoryId === c.id)
            .map(resolveProduct),
    };
}

// ─── Root resolver ────────────────────────────────────────────────────────────

const rootValue = {
    // Queries
    products({ categoryId }) {
        const list = categoryId
            ? store.products.filter(p => p.categoryId === categoryId)
            : store.products;
        return list.map(resolveProduct);
    },

    product({ id }) {
        const p = findById(store.products, id);
        return p ? resolveProduct(p) : null;
    },

    categories() {
        return store.categories.map(resolveCategory);
    },

    category({ id }) {
        const c = findById(store.categories, id);
        return c ? resolveCategory(c) : null;
    },

    user({ id }) {
        const u = findById(store.users, id);
        return resolveUser(u);
    },

    order({ id }) {
        const o = findById(store.orders, id);
        return o ? resolveOrder(o) : null;
    },

    ordersByUser({ userId }) {
        return store.orders
            .filter(o => o.userId === userId)
            .map(resolveOrder);
    },

    // Mutations
    placeOrder({ userId, items }) {
        const user = findById(store.users, userId);
        if (!user) throw new Error(`User ${userId} not found`);

        let total = 0;
        const resolvedItems = items.map(({ productId, quantity }) => {
            const product = findById(store.products, productId);
            if (!product) throw new Error(`Product ${productId} not found`);
            const unitPrice = product.price;
            total += unitPrice * quantity;
            return { productId, quantity, unitPrice };
        });

        const newOrder = {
            id:     `order-${store.nextOrderId++}`,
            userId,
            status: 'PENDING',
            total:  Math.round(total * 100) / 100,
            items:  resolvedItems,
        };
        store.orders.push(newOrder);
        return resolveOrder(newOrder);
    },

    updateOrderStatus({ orderId, status }) {
        const order = findById(store.orders, orderId);
        if (!order) throw new Error(`Order ${orderId} not found`);
        order.status = status;
        return resolveOrder(order);
    },
};

// ─── Public API ───────────────────────────────────────────────────────────────

/**
 * Execute a GraphQL query or mutation against the mock service.
 *
 * @param {string} query      - GraphQL query/mutation string
 * @param {object} [variables] - Optional variables map
 * @returns {Promise<{data, errors}>}
 */
async function execute(query, variables = {}) {
    return graphql({
        schema,
        source:    query,
        rootValue,
        variableValues: variables,
    });
}

module.exports = { execute, resetStore };
