// Sample eCommerce data used by the GraphQL mock service

const categories = [
    { id: 'cat-1', name: 'Electronics', description: 'Electronic devices and accessories' },
    { id: 'cat-2', name: 'Clothing',    description: 'Apparel and fashion items' },
    { id: 'cat-3', name: 'Books',       description: 'Physical and digital books' },
];

const products = [
    {
        id: 'prod-1',
        name: 'Wireless Headphones',
        description: 'Noise-cancelling over-ear headphones',
        price: 99.99,
        stock: 42,
        categoryId: 'cat-1',
    },
    {
        id: 'prod-2',
        name: 'Mechanical Keyboard',
        description: 'Compact TKL mechanical keyboard with RGB',
        price: 129.99,
        stock: 15,
        categoryId: 'cat-1',
    },
    {
        id: 'prod-3',
        name: 'Cotton T-Shirt',
        description: 'Classic fit 100% cotton t-shirt',
        price: 19.99,
        stock: 200,
        categoryId: 'cat-2',
    },
    {
        id: 'prod-4',
        name: 'Clean Code',
        description: 'A handbook of agile software craftsmanship by Robert C. Martin',
        price: 34.99,
        stock: 0,
        categoryId: 'cat-3',
    },
    {
        id: 'prod-5',
        name: 'USB-C Hub',
        description: '7-in-1 multiport USB-C hub',
        price: 49.99,
        stock: 88,
        categoryId: 'cat-1',
    },
];

const users = [
    { id: 'user-1', name: 'Alice Johnson', email: 'alice@example.com' },
    { id: 'user-2', name: 'Bob Smith',     email: 'bob@example.com' },
];

const orders = [
    {
        id: 'order-1',
        userId: 'user-1',
        status: 'DELIVERED',
        total: 229.98,
        items: [
            { productId: 'prod-1', quantity: 1, unitPrice: 99.99 },
            { productId: 'prod-2', quantity: 1, unitPrice: 129.99 },
        ],
    },
    {
        id: 'order-2',
        userId: 'user-2',
        status: 'PENDING',
        total: 19.99,
        items: [
            { productId: 'prod-3', quantity: 1, unitPrice: 19.99 },
        ],
    },
];

module.exports = { categories, products, users, orders };
