const express = require('express');
const app = express();

app.use(express.json());

// In-memory data stores
const customers = {};
const carts = {};
const payments = {};
const orders = {};

// Pre-loaded menu items
const menu = [
  { id: 1, name: 'Classic Burger', price: 8.99, category: 'Burgers' },
  { id: 2, name: 'Cheese Burger', price: 9.99, category: 'Burgers' },
  { id: 3, name: 'Chicken Nuggets', price: 5.49, category: 'Sides' },
  { id: 4, name: 'French Fries', price: 3.99, category: 'Sides' },
  { id: 5, name: 'Cola', price: 2.49, category: 'Drinks' },
  { id: 6, name: 'Lemonade', price: 2.99, category: 'Drinks' }
];

// Valid payment methods and order statuses
const validPaymentMethods = ['credit_card', 'debit_card', 'cash'];
const orderStatusFlow = ['pending', 'confirmed', 'preparing', 'ready', 'delivered'];

// ID counters
let customerIdCounter = 1;
let cartIdCounter = 1;
let paymentIdCounter = 1;
let orderIdCounter = 1;

// ==================== CUSTOMER SERVICE ====================

// POST /api/customers - Create a new customer
app.post('/api/customers', (req, res) => {
  const { name, email, phone } = req.body;

  if (!name || !email) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Name and email are required'
    });
  }

  const id = `cust_${String(customerIdCounter++).padStart(3, '0')}`;
  const customer = {
    id,
    name,
    email,
    phone: phone || null,
    createdAt: new Date().toISOString()
  };

  customers[id] = customer;
  res.status(201).json(customer);
});

// GET /api/customers - List all customers
app.get('/api/customers', (req, res) => {
  res.json(Object.values(customers));
});

// GET /api/customers/:id - Get customer by ID
app.get('/api/customers/:id', (req, res) => {
  const customer = customers[req.params.id];
  if (!customer) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Customer with id "${req.params.id}" not found`
    });
  }
  res.json(customer);
});

// ==================== MENU SERVICE ====================

// GET /api/menu - List all menu items
app.get('/api/menu', (req, res) => {
  res.json(menu);
});

// GET /api/menu/:id - Get a specific menu item
app.get('/api/menu/:id', (req, res) => {
  const item = menu.find(i => i.id === parseInt(req.params.id));
  if (!item) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Menu item with id "${req.params.id}" not found`
    });
  }
  res.json(item);
});

// ==================== CART SERVICE ====================

// POST /api/carts - Create a new cart
app.post('/api/carts', (req, res) => {
  const { customerId } = req.body;

  if (!customerId) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Customer ID is required'
    });
  }

  if (!customers[customerId]) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Customer with id "${customerId}" not found`
    });
  }

  const id = `cart_${String(cartIdCounter++).padStart(3, '0')}`;
  const cart = {
    id,
    customerId,
    items: [],
    total: 0,
    createdAt: new Date().toISOString()
  };

  carts[id] = cart;
  res.status(201).json(cart);
});

// GET /api/carts/:id - Get cart by ID
app.get('/api/carts/:id', (req, res) => {
  const cart = carts[req.params.id];
  if (!cart) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Cart with id "${req.params.id}" not found`
    });
  }
  res.json(cart);
});

// POST /api/carts/:id/items - Add item to cart
app.post('/api/carts/:id/items', (req, res) => {
  const cart = carts[req.params.id];
  if (!cart) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Cart with id "${req.params.id}" not found`
    });
  }

  const { menuItemId, quantity } = req.body;

  if (!menuItemId || !quantity) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Menu item ID and quantity are required'
    });
  }

  if (quantity <= 0) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Quantity must be greater than 0'
    });
  }

  const menuItem = menu.find(i => i.id === menuItemId);
  if (!menuItem) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Menu item with id "${menuItemId}" not found`
    });
  }

  // Check if item already exists in cart
  const existingItem = cart.items.find(i => i.menuItemId === menuItemId);
  if (existingItem) {
    existingItem.quantity += quantity;
  } else {
    cart.items.push({
      menuItemId: menuItem.id,
      name: menuItem.name,
      quantity,
      price: menuItem.price
    });
  }

  // Recalculate total
  cart.total = cart.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
  // Round to 2 decimal places to avoid floating point issues
  cart.total = Math.round(cart.total * 100) / 100;

  res.json(cart);
});

// PUT /api/carts/:id/items/:itemIndex - Update cart item quantity
app.put('/api/carts/:id/items/:itemIndex', (req, res) => {
  const cart = carts[req.params.id];
  if (!cart) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Cart with id "${req.params.id}" not found`
    });
  }

  const { quantity } = req.body;
  const itemIndex = parseInt(req.params.itemIndex);

  if (isNaN(itemIndex) || itemIndex < 0 || itemIndex >= cart.items.length) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Item at index ${itemIndex} not found in cart`
    });
  }

  if (quantity === undefined || quantity === null) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Quantity is required'
    });
  }

  if (quantity <= 0) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Quantity must be greater than 0'
    });
  }

  cart.items[itemIndex].quantity = quantity;

  // Recalculate total
  cart.total = cart.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
  cart.total = Math.round(cart.total * 100) / 100;

  res.json(cart);
});

// DELETE /api/carts/:id/items/:itemIndex - Remove item from cart
app.delete('/api/carts/:id/items/:itemIndex', (req, res) => {
  const cart = carts[req.params.id];
  if (!cart) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Cart with id "${req.params.id}" not found`
    });
  }

  const itemIndex = parseInt(req.params.itemIndex);

  if (isNaN(itemIndex) || itemIndex < 0 || itemIndex >= cart.items.length) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Item at index ${itemIndex} not found in cart`
    });
  }

  cart.items.splice(itemIndex, 1);

  // Recalculate total
  cart.total = cart.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
  cart.total = Math.round(cart.total * 100) / 100;

  res.json(cart);
});

// DELETE /api/carts/:id - Delete/empty the cart
app.delete('/api/carts/:id', (req, res) => {
  const cart = carts[req.params.id];
  if (!cart) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Cart with id "${req.params.id}" not found`
    });
  }

  delete carts[req.params.id];
  res.status(204).send();
});

// ==================== PAYMENT SERVICE ====================

// POST /api/payments - Process a payment
app.post('/api/payments', (req, res) => {
  const { customerId, amount, paymentMethod } = req.body;

  if (!customerId || !amount || !paymentMethod) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Customer ID, amount, and payment method are required'
    });
  }

  if (!customers[customerId]) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Customer with id "${customerId}" not found`
    });
  }

  if (amount <= 0) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Amount must be greater than 0'
    });
  }

  if (!validPaymentMethods.includes(paymentMethod)) {
    return res.status(422).json({
      error: 'Validation Error',
      message: `Invalid payment method. Accepted: ${validPaymentMethods.join(', ')}`
    });
  }

  const id = `pay_${String(paymentIdCounter++).padStart(3, '0')}`;
  const payment = {
    id,
    customerId,
    amount,
    paymentMethod,
    status: 'approved',
    processedAt: new Date().toISOString()
  };

  payments[id] = payment;
  res.status(201).json(payment);
});

// GET /api/payments/:id - Get payment status
app.get('/api/payments/:id', (req, res) => {
  const payment = payments[req.params.id];
  if (!payment) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Payment with id "${req.params.id}" not found`
    });
  }
  res.json(payment);
});

// ==================== ORDER SERVICE ====================

// POST /api/orders - Place an order
app.post('/api/orders', (req, res) => {
  const { customerId, cartId, paymentId } = req.body;

  if (!customerId || !cartId || !paymentId) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Customer ID, cart ID, and payment ID are required'
    });
  }

  if (!customers[customerId]) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Customer with id "${customerId}" not found`
    });
  }

  const cart = carts[cartId];
  if (!cart) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Cart with id "${cartId}" not found`
    });
  }

  if (cart.customerId !== customerId) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Cart does not belong to the specified customer'
    });
  }

  if (cart.items.length === 0) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Cart is empty. Add items before placing an order'
    });
  }

  const payment = payments[paymentId];
  if (!payment) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Payment with id "${paymentId}" not found`
    });
  }

  if (payment.customerId !== customerId) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Payment does not belong to the specified customer'
    });
  }

  if (payment.status !== 'approved') {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Payment has not been approved'
    });
  }

  const id = `order_${String(orderIdCounter++).padStart(3, '0')}`;
  const order = {
    id,
    customerId,
    cartId,
    paymentId,
    status: 'confirmed',
    total: cart.total,
    items: JSON.parse(JSON.stringify(cart.items)), // Deep copy
    createdAt: new Date().toISOString()
  };

  orders[id] = order;
  res.status(201).json(order);
});

// GET /api/orders - List all orders
app.get('/api/orders', (req, res) => {
  res.json(Object.values(orders));
});

// GET /api/orders/:id - Get order by ID
app.get('/api/orders/:id', (req, res) => {
  const order = orders[req.params.id];
  if (!order) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Order with id "${req.params.id}" not found`
    });
  }
  res.json(order);
});

// PUT /api/orders/:id - Update order status
app.put('/api/orders/:id', (req, res) => {
  const order = orders[req.params.id];
  if (!order) {
    return res.status(404).json({
      error: 'Not Found',
      message: `Order with id "${req.params.id}" not found`
    });
  }

  const { status } = req.body;

  if (!status) {
    return res.status(422).json({
      error: 'Validation Error',
      message: 'Status is required'
    });
  }

  if (!orderStatusFlow.includes(status)) {
    return res.status(422).json({
      error: 'Validation Error',
      message: `Invalid status. Valid statuses: ${orderStatusFlow.join(' -> ')}`
    });
  }

  // Check status transition is valid (must progress forward)
  const currentStatusIndex = orderStatusFlow.indexOf(order.status);
  const newStatusIndex = orderStatusFlow.indexOf(status);

  if (newStatusIndex <= currentStatusIndex) {
    return res.status(422).json({
      error: 'Validation Error',
      message: `Cannot transition from "${order.status}" to "${status}". Status must progress forward.`
    });
  }

  // Status must be exactly one step ahead
  if (newStatusIndex !== currentStatusIndex + 1) {
    return res.status(422).json({
      error: 'Validation Error',
      message: `Cannot skip statuses. Next valid status from "${order.status}" is "${orderStatusFlow[currentStatusIndex + 1]}"`
    });
  }

  order.status = status;
  res.json(order);
});

// ==================== START SERVER ====================

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`E-Commerce Mock API server running on http://localhost:${PORT}`);
  console.log(`Menu items loaded: ${menu.length} items`);
});

module.exports = app;