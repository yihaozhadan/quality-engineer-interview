# Solution Evaluation Guide

This guide helps you compare candidate solutions against the reference implementations.

---

## Exercise 1: Product Search - Evaluation Checklist

### ✅ Must Have (Core Requirements)

**testSearchProductsByCategory:**
- [ ] Uses `GraphQLQueries.searchProductsByCategory("Electronics")`
- [ ] Checks response status code is 200
- [ ] Verifies no GraphQL errors using `assertNoErrors()` or similar
- [ ] Extracts products list from `data.products` path
- [ ] Asserts products list is not empty
- [ ] Verifies all products have category "Electronics"

**testGetProductById:**
- [ ] Uses `GraphQLQueries.getProductById("PROD-001")`
- [ ] Checks response status code
- [ ] Extracts product from `data.product` path
- [ ] Verifies product is not null
- [ ] Checks required fields: id, name, price, stock

**testGetProductById_InvalidId:**
- [ ] Uses invalid ID like "INVALID-ID"
- [ ] Handles response appropriately (null product OR errors present)
- [ ] Understands GraphQL can return 200 with errors in body

### ⭐ Good to Have (Quality Indicators)

- Uses descriptive assertion messages with `.as()` or similar
- Validates data types and ranges (e.g., price > 0, stock >= 0)
- Clean code organization with clear steps
- Uses helper methods from `BaseGraphQLTest`
- Proper variable naming
- Implements bonus test for price filters

### 🚩 Red Flags

- Only checks for null without validating actual values
- Doesn't handle GraphQL errors properly
- Hardcoded JSON paths without using response object properly
- No consideration for edge cases
- Copy-pasted code without understanding
- Tests would fail on valid responses

---

## Exercise 2: Cart Operations - Evaluation Checklist

### ✅ Must Have (Core Requirements)

**testAddItemToCart:**
- [ ] Generates unique user ID with `generateUserId()` or similar
- [ ] Uses `GraphQLQueries.addToCart()` mutation
- [ ] Verifies cart is created with items
- [ ] Checks item quantity matches what was added
- [ ] Validates totalPrice calculation

**testUpdateCartItemQuantity:**
- [ ] First adds item to cart (setup)
- [ ] Then updates quantity using `updateCartItem()`
- [ ] Verifies quantity is updated
- [ ] Confirms totalPrice is recalculated

**testRemoveItemFromCart:**
- [ ] Adds multiple items to cart
- [ ] Removes one item
- [ ] Verifies removed item is gone
- [ ] Confirms other items remain
- [ ] Checks totalPrice is adjusted

**testAddInvalidProductToCart:**
- [ ] Attempts to add invalid product ID
- [ ] Handles error response appropriately

### ⭐ Good to Have (Quality Indicators)

- **Test Isolation**: Each test uses unique user ID
- **State Management**: Properly chains API calls
- **Calculation Validation**: Verifies subtotal = price × quantity
- **Total Validation**: Confirms totalPrice = sum of all subtotals
- **Clear Setup**: Readable test structure with setup/action/verify
- Implements bonus tests (zero quantity, negative quantity)
- Validates cart item structure (productId, productName, etc.)

### 🚩 Red Flags

- Reuses same user ID across tests (no isolation)
- Doesn't validate calculated fields
- Skips verification of state changes
- Only tests happy path, no edge cases
- Doesn't chain operations properly
- Missing assertions on critical fields

---

## Exercise 3: Order Workflow - Evaluation Checklist

### ✅ Must Have (Core Requirements)

**testCompleteOrderWorkflow:**
- [ ] Adds multiple products to cart
- [ ] Creates order from cart using `createOrder()`
- [ ] Verifies order is created successfully
- [ ] Checks order has correct userId
- [ ] Validates order status is "PENDING"
- [ ] Confirms order contains all cart items
- [ ] Verifies order totalPrice matches cart total
- [ ] Checks shipping address is correct

**testRetrieveOrderById:**
- [ ] Creates an order (setup)
- [ ] Extracts order ID
- [ ] Retrieves order using `getOrder()`
- [ ] Verifies retrieved order matches created order

### ⭐ Good to Have (Quality Indicators)

- **Workflow Design**: Logical sequence of operations
- **State Validation**: Checks cart state before creating order
- **Data Matching**: Verifies items in order match cart items
- **Comprehensive Checks**: Validates all order fields
- **Realistic Scenarios**: Uses realistic test data
- Implements bonus tests (empty cart, invalid address)
- Validates order timestamp/orderDate
- Tests multiple orders for same user

### 🚩 Red Flags

- Doesn't verify cart contents before order creation
- Missing validation of order-cart relationship
- Skips status verification
- No shipping address validation
- Incomplete order retrieval test
- Doesn't handle edge cases

---

## Overall Code Quality Assessment

### Structure & Organization (Weight: 25%)

**Excellent (5/5):**
- Clear test structure with setup/action/verification
- Descriptive test names and assertion messages
- Logical grouping and flow
- Good use of helper methods

**Good (4/5):**
- Reasonable structure
- Most tests are clear
- Some helper method usage

**Adequate (3/5):**
- Basic organization
- Tests work but could be clearer
- Minimal helper usage

**Poor (1-2/5):**
- Disorganized code
- Unclear test flow
- No structure

### Assertions (Weight: 30%)

**Excellent (5/5):**
- Comprehensive assertions covering all key fields
- Validates data types, ranges, and calculations
- Meaningful assertion messages
- Tests both positive and negative cases

**Good (4/5):**
- Most important fields checked
- Some edge case handling
- Adequate assertion coverage

**Adequate (3/5):**
- Basic assertions (not null, equals)
- Missing some validations
- Limited edge case coverage

**Poor (1-2/5):**
- Only null checks
- Missing critical validations
- No edge cases

### GraphQL Understanding (Weight: 25%)

**Excellent (5/5):**
- Proper error handling (200 with errors)
- Correct use of query vs mutation
- Understands data/errors response structure
- Good JSON path usage

**Good (4/5):**
- Handles most GraphQL patterns correctly
- Minor misunderstandings
- Works with response structure

**Adequate (3/5):**
- Basic GraphQL operations work
- Some confusion on error handling
- Limited understanding of structure

**Poor (1-2/5):**
- Doesn't understand GraphQL errors
- Incorrect query/mutation usage
- Can't navigate response properly

### Problem Solving (Weight: 20%)

**Excellent (5/5):**
- Handles edge cases proactively
- Good debugging approach
- Adapts to challenges quickly
- Thinks about test data management

**Good (4/5):**
- Solves most problems
- Asks good questions
- Some edge case consideration

**Adequate (3/5):**
- Gets through basic scenarios
- Needs hints for edge cases
- Limited problem anticipation

**Poor (1-2/5):**
- Struggles with basic issues
- Can't debug effectively
- No edge case awareness

---

## Scoring Guide

### Exercise 1: Product Search (30 points)
- Basic tests (15 pts): 3 tests implemented correctly
- Quality (10 pts): Assertions, error handling, structure
- Bonus (5 pts): Price filter test or additional validations

### Exercise 2: Cart Operations (40 points)
- Core tests (20 pts): 4 tests with state management
- Quality (15 pts): Test isolation, calculations, assertions
- Bonus (5 pts): Edge cases, validation tests

### Exercise 3: Order Workflow (30 points - BONUS)
- Workflow test (15 pts): Complete E2E flow
- Retrieval test (10 pts): Order query and validation
- Quality (5 pts): Comprehensive checks, edge cases

**Total: 100 points**

### Final Recommendation

**90-100: Strong Hire** ⭐
- Completed all exercises with high quality
- Excellent code organization and assertions
- Strong GraphQL understanding
- Proactive edge case handling

**75-89: Hire** ✅
- Completed Exercise 1 & 2 well
- Good code quality
- Solid technical skills
- Some minor gaps

**60-74: Borderline** 🤔
- Basic functionality works
- Some quality issues
- Needs more experience
- Consider team needs

**Below 60: No Hire** ❌
- Cannot complete basic exercises
- Poor code quality
- Fundamental skill gaps
- Not ready for role

---

## Common Excellent Solutions

Look for candidates who:
1. **Start with test isolation** - immediately use `generateUserId()`
2. **Think about calculations** - validate totalPrice, subtotal
3. **Handle GraphQL properly** - understand 200 + errors pattern
4. **Write readable code** - clear names, good structure
5. **Consider edge cases** - without being prompted
6. **Use helpers effectively** - leverage `BaseGraphQLTest`
7. **Explain their thinking** - talk through their approach

## Common Weak Solutions

Watch for candidates who:
1. **Skip test isolation** - reuse same user IDs
2. **Only null checks** - no real validation
3. **Ignore errors** - don't check GraphQL errors
4. **Poor organization** - messy, hard to follow
5. **No edge cases** - only happy path
6. **Don't use helpers** - reinvent everything
7. **Can't explain** - just coding without thinking

---

## Discussion Topics Based on Solutions

### If they did well:
- "How would you improve test execution speed?"
- "How would you handle schema versioning?"
- "What metrics would you track for these tests?"

### If they struggled:
- "What would you do differently given more time?"
- "How do you typically approach learning new frameworks?"
- "What resources do you use when stuck?"

### For specific weaknesses:
- **Weak assertions**: "How do you decide what to assert?"
- **No test isolation**: "What problems could shared state cause?"
- **Poor GraphQL handling**: "How does GraphQL error handling differ from REST?"
