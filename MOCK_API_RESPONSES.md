# Mock API Response Examples

This document shows example responses for the GraphQL API to help understand the expected data structure.

## Product Queries

### Search Products by Category
**Query:**
```graphql
{
  products(category: "Electronics") {
    id
    name
    category
    price
    stock
  }
}
```

**Response:**
```json
{
  "data": {
    "products": [
      {
        "id": "PROD-001",
        "name": "Laptop Pro 15",
        "category": "Electronics",
        "price": 1299.99,
        "stock": 15
      },
      {
        "id": "PROD-002",
        "name": "Wireless Mouse",
        "category": "Electronics",
        "price": 29.99,
        "stock": 50
      },
      {
        "id": "PROD-003",
        "name": "USB-C Hub",
        "category": "Electronics",
        "price": 49.99,
        "stock": 30
      }
    ]
  }
}
```

### Get Product by ID
**Query:**
```graphql
{
  product(id: "PROD-001") {
    id
    name
    category
    price
    stock
    description
  }
}
```

**Response:**
```json
{
  "data": {
    "product": {
      "id": "PROD-001",
      "name": "Laptop Pro 15",
      "category": "Electronics",
      "price": 1299.99,
      "stock": 15,
      "description": "High-performance laptop with 15-inch display"
    }
  }
}
```

### Invalid Product ID
**Response:**
```json
{
  "data": {
    "product": null
  },
  "errors": [
    {
      "message": "Product not found",
      "path": ["product"],
      "extensions": {
        "code": "NOT_FOUND"
      }
    }
  ]
}
```

## Cart Mutations

### Add to Cart
**Mutation:**
```graphql
mutation {
  addToCart(userId: "user_123", productId: "PROD-001", quantity: 2) {
    id
    userId
    items {
      productId
      productName
      quantity
      price
      subtotal
    }
    totalPrice
  }
}
```

**Response:**
```json
{
  "data": {
    "addToCart": {
      "id": "cart_123",
      "userId": "user_123",
      "items": [
        {
          "productId": "PROD-001",
          "productName": "Laptop Pro 15",
          "quantity": 2,
          "price": 1299.99,
          "subtotal": 2599.98
        }
      ],
      "totalPrice": 2599.98
    }
  }
}
```

### Get Cart
**Query:**
```graphql
{
  cart(userId: "user_123") {
    id
    userId
    items {
      productId
      productName
      quantity
      price
      subtotal
    }
    totalPrice
  }
}
```

**Response:**
```json
{
  "data": {
    "cart": {
      "id": "cart_123",
      "userId": "user_123",
      "items": [
        {
          "productId": "PROD-001",
          "productName": "Laptop Pro 15",
          "quantity": 2,
          "price": 1299.99,
          "subtotal": 2599.98
        },
        {
          "productId": "PROD-002",
          "productName": "Wireless Mouse",
          "quantity": 1,
          "price": 29.99,
          "subtotal": 29.99
        }
      ],
      "totalPrice": 2629.97
    }
  }
}
```

### Update Cart Item
**Mutation:**
```graphql
mutation {
  updateCartItem(userId: "user_123", productId: "PROD-001", quantity: 5) {
    id
    userId
    items {
      productId
      productName
      quantity
      price
      subtotal
    }
    totalPrice
  }
}
```

**Response:**
```json
{
  "data": {
    "updateCartItem": {
      "id": "cart_123",
      "userId": "user_123",
      "items": [
        {
          "productId": "PROD-001",
          "productName": "Laptop Pro 15",
          "quantity": 5,
          "price": 1299.99,
          "subtotal": 6499.95
        },
        {
          "productId": "PROD-002",
          "productName": "Wireless Mouse",
          "quantity": 1,
          "price": 29.99,
          "subtotal": 29.99
        }
      ],
      "totalPrice": 6529.94
    }
  }
}
```

### Remove from Cart
**Mutation:**
```graphql
mutation {
  removeFromCart(userId: "user_123", productId: "PROD-002") {
    id
    userId
    items {
      productId
      productName
      quantity
      price
      subtotal
    }
    totalPrice
  }
}
```

**Response:**
```json
{
  "data": {
    "removeFromCart": {
      "id": "cart_123",
      "userId": "user_123",
      "items": [
        {
          "productId": "PROD-001",
          "productName": "Laptop Pro 15",
          "quantity": 5,
          "price": 1299.99,
          "subtotal": 6499.95
        }
      ],
      "totalPrice": 6499.95
    }
  }
}
```

## Order Mutations

### Create Order
**Mutation:**
```graphql
mutation {
  createOrder(userId: "user_123", shippingAddress: "123 Main St, City, State 12345") {
    id
    userId
    orderDate
    status
    items {
      productId
      productName
      quantity
      price
      subtotal
    }
    totalPrice
    shippingAddress
  }
}
```

**Response:**
```json
{
  "data": {
    "createOrder": {
      "id": "order_456",
      "userId": "user_123",
      "orderDate": "2026-02-05T10:30:00Z",
      "status": "PENDING",
      "items": [
        {
          "productId": "PROD-001",
          "productName": "Laptop Pro 15",
          "quantity": 5,
          "price": 1299.99,
          "subtotal": 6499.95
        }
      ],
      "totalPrice": 6499.95,
      "shippingAddress": "123 Main St, City, State 12345"
    }
  }
}
```

### Get Order
**Query:**
```graphql
{
  order(id: "order_456") {
    id
    userId
    orderDate
    status
    items {
      productId
      productName
      quantity
      price
      subtotal
    }
    totalPrice
    shippingAddress
  }
}
```

**Response:**
```json
{
  "data": {
    "order": {
      "id": "order_456",
      "userId": "user_123",
      "orderDate": "2026-02-05T10:30:00Z",
      "status": "PENDING",
      "items": [
        {
          "productId": "PROD-001",
          "productName": "Laptop Pro 15",
          "quantity": 5,
          "price": 1299.99,
          "subtotal": 6499.95
        }
      ],
      "totalPrice": 6499.95,
      "shippingAddress": "123 Main St, City, State 12345"
    }
  }
}
```

## Error Responses

### Invalid Product ID in Cart
**Response:**
```json
{
  "data": {
    "addToCart": null
  },
  "errors": [
    {
      "message": "Product INVALID-PROD not found",
      "path": ["addToCart"],
      "extensions": {
        "code": "PRODUCT_NOT_FOUND"
      }
    }
  ]
}
```

### Insufficient Stock
**Response:**
```json
{
  "data": {
    "addToCart": null
  },
  "errors": [
    {
      "message": "Insufficient stock. Available: 5, Requested: 10",
      "path": ["addToCart"],
      "extensions": {
        "code": "INSUFFICIENT_STOCK",
        "available": 5,
        "requested": 10
      }
    }
  ]
}
```

### Invalid Quantity
**Response:**
```json
{
  "data": {
    "addToCart": null
  },
  "errors": [
    {
      "message": "Quantity must be greater than 0",
      "path": ["addToCart"],
      "extensions": {
        "code": "INVALID_INPUT"
      }
    }
  ]
}
```
