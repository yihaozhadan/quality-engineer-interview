# eCommerce GraphQL API Testing - Interview Exercise

## Overview
This exercise simulates testing a real eCommerce platform's GraphQL API. The candidate will work with existing test infrastructure and implement automated tests for various scenarios.

## Time Allocation (60 minutes)
- **5 min**: Setup and environment familiarization
- **40 min**: Coding exercises (2-3 problems)
- **15 min**: Discussion and code review

## Technology Stack
- Java 11+
- JUnit 5
- Rest Assured (with GraphQL support)
- Maven

## Setup Instructions
1. Import the project into your IDE
2. Run `mvn clean install` to download dependencies
3. Review the existing `BaseGraphQLTest.java` for helper methods
4. Mock API endpoint: `http://localhost:8080/graphql` (simulated)

## Project Structure
```
src/
├── main/java/
│   └── com/ecommerce/api/
│       ├── models/          # API response models
│       └── queries/         # GraphQL query templates
└── test/java/
    └── com/ecommerce/tests/
        ├── BaseGraphQLTest.java    # Base test class with utilities
        └── exercises/              # Your test implementations go here
```

## Exercise Files
- `Exercise1_ProductSearch.java` - Basic GraphQL query testing
- `Exercise2_CartOperations.java` - Mutation testing with state management
- `Exercise3_OrderWorkflow.java` - End-to-end workflow testing (Bonus)

## Evaluation Criteria
- Code organization and readability
- Proper use of assertions
- Error handling
- Test data management
- Understanding of GraphQL concepts
- API testing best practices
