# Interviewer Guide - Automation Test Engineer Interview

## Interview Structure (60 minutes)

### 1. Introduction (5 minutes)
- Introduce yourself and explain the interview format
- Give candidate time to review the codebase
- Answer any clarifying questions about the project structure
- Ensure their IDE is set up correctly

### 2. Coding Exercises (40 minutes)

#### Exercise 1: Product Search (15 min) - REQUIRED
**Difficulty**: Easy to Medium
**Purpose**: Assess basic GraphQL testing knowledge and API testing fundamentals

**What to observe**:
- [ ] Can they navigate the existing codebase?
- [ ] Do they understand how to use the helper classes (GraphQLQueries, BaseGraphQLTest)?
- [ ] Can they write proper assertions?
- [ ] Do they handle response extraction correctly?
- [ ] Do they validate both happy path and error cases?

**Key evaluation points**:
- Understanding of GraphQL query structure
- Proper use of Rest Assured
- Assertion quality (using AssertJ or similar)
- Code readability and organization

**Common mistakes to watch for**:
- Not checking for GraphQL errors in response (errors can exist with 200 status)
- Improper JSON path extraction
- Weak assertions (only checking null vs actual values)
- Not validating all required fields

---

#### Exercise 2: Cart Operations (20 min) - REQUIRED
**Difficulty**: Medium
**Purpose**: Assess ability to test stateful operations and mutations

**What to observe**:
- [ ] Can they chain multiple API calls?
- [ ] Do they maintain test data isolation (unique user IDs)?
- [ ] Can they test mutations (add, update, remove)?
- [ ] Do they validate state changes correctly?
- [ ] Can they calculate and verify totalPrice correctly?

**Key evaluation points**:
- State management in tests
- Mutation testing skills
- Data validation across operations
- Understanding of eCommerce workflows

**Common mistakes to watch for**:
- Not using unique user IDs (tests would interfere with each other)
- Not verifying calculated fields (totalPrice, subtotal)
- Missing edge case validations
- Not cleaning up or isolating test data

---

#### Exercise 3: Order Workflow (15 min) - BONUS/Optional
**Difficulty**: Medium to Hard
**Purpose**: Assess end-to-end testing skills

**What to observe**:
- [ ] Can they design an E2E test flow?
- [ ] Do they properly sequence operations (cart → order)?
- [ ] Can they validate complex nested structures?
- [ ] Do they consider realistic test data?

**Only attempt if candidate completes first two exercises quickly**

---

### 3. Code Review & Discussion (15 minutes)

#### Technical Discussion Questions:
1. **GraphQL Specifics**:
   - "How does GraphQL error handling differ from REST API?"
   - "What are the benefits of GraphQL for test automation?"
   - "How would you handle GraphQL schema changes in your tests?"

2. **Test Design**:
   - "How would you organize tests for better maintainability?"
   - "What test data strategy would you use for this eCommerce API?"
   - "How would you handle test dependencies and execution order?"

3. **Framework & Tools**:
   - "Why use Rest Assured for GraphQL instead of a GraphQL-specific client?"
   - "How would you implement retry logic for flaky tests?"
   - "What reporting would you add to this test suite?"

4. **CI/CD Integration**:
   - "How would you integrate these tests into a CI/CD pipeline?"
   - "How would you handle different environments (dev, staging, prod)?"
   - "What metrics would you track for these tests?"

5. **Advanced Scenarios**:
   - "How would you test GraphQL subscriptions (WebSocket)?"
   - "How would you handle authentication in GraphQL tests?"
   - "How would you implement parallel test execution?"

---

## Evaluation Rubric

### Code Quality (30 points)
- [ ] Clean, readable code (10 pts)
- [ ] Proper naming conventions (5 pts)
- [ ] Good code organization (5 pts)
- [ ] Appropriate use of helper methods (5 pts)
- [ ] Error handling (5 pts)

### Technical Skills (40 points)
- [ ] GraphQL understanding (10 pts)
- [ ] Rest Assured proficiency (10 pts)
- [ ] Assertion quality (10 pts)
- [ ] Test design (10 pts)

### Problem Solving (20 points)
- [ ] Approach to debugging (5 pts)
- [ ] Ability to handle ambiguity (5 pts)
- [ ] Edge case consideration (5 pts)
- [ ] Completion speed (5 pts)

### Communication (10 points)
- [ ] Explains thought process (5 pts)
- [ ] Asks clarifying questions (5 pts)

**Total: 100 points**

**Scoring**:
- 80-100: Strong hire
- 60-79: Hire
- 40-59: Borderline (additional interview recommended)
- Below 40: Do not hire

---

## Tips for Interviewers

### Creating a Comfortable Environment:
1. Remind candidates they can ask questions anytime
2. It's okay if they don't complete all exercises
3. Thinking out loud is encouraged
4. They can use Google/documentation (but mention this uses judgment)

### Providing Hints (if needed):
- **If stuck on GraphQL syntax**: Point them to GraphQLQueries class
- **If struggling with assertions**: Show them AssertJ documentation
- **If confused about response structure**: Help them examine a sample response
- **If lost in JSON path**: Give one example of extracting data

### Red Flags:
- Cannot understand basic GraphQL structure
- Doesn't know how to write basic assertions
- Cannot debug simple JSON path issues
- Doesn't consider edge cases at all
- Poor code organization with no structure
- Cannot explain their thought process

### Green Flags:
- Asks about test data management early
- Considers edge cases without prompting
- Writes clean, self-documenting code
- Uses helper methods effectively
- Thinks about maintainability
- Explains trade-offs in their approach

---

## Sample Questions for Discussion

### After Exercise 1:
- "I noticed you used this assertion style. Why did you choose it?"
- "How would you handle if the API started returning paginated results?"
- "What would you do if product categories were dynamic?"

### After Exercise 2:
- "How would you prevent test data conflicts in a shared environment?"
- "What if we needed to test cart expiration after 30 minutes?"
- "How would you verify that inventory decreases after adding to cart?"

### After Exercise 3 (if completed):
- "How would you test order status transitions (PENDING → SHIPPED → DELIVERED)?"
- "What additional validations would you add for production?"
- "How would you handle testing payment integration?"

---

## Alternative Exercises (if needed)

If candidate finishes early or you want to test additional skills:

### Additional Exercise A: Schema Validation
Ask them to write a test that validates the GraphQL schema introspection.

### Additional Exercise B: Performance Testing
Ask how they would add response time assertions to existing tests.

### Additional Exercise C: Data-Driven Testing
Ask them to refactor a test to be data-driven with multiple test cases.

---

## Post-Interview

### Feedback Template:
```
Candidate: [Name]
Position: Automation Test Engineer
Date: [Date]

Technical Skills:
- GraphQL Knowledge: [Rating 1-5]
- Test Automation: [Rating 1-5]
- Java Proficiency: [Rating 1-5]
- Problem Solving: [Rating 1-5]

Strengths:
- [List 2-3 strengths]

Areas for Development:
- [List 2-3 areas]

Exercises Completed:
- Exercise 1: [Completed/Partial/Not Completed]
- Exercise 2: [Completed/Partial/Not Completed]
- Exercise 3: [Completed/Partial/Not Completed]

Overall Score: [X/100]
Recommendation: [Strong Hire/Hire/Borderline/Do Not Hire]

Additional Comments:
[Free form]
```
