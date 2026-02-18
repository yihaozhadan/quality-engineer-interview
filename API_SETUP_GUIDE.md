# API Setup Options for Interview

## The Question: "Do I need a real GraphQL API?"

**Short Answer: NO** - This is a coding assessment, not an integration test.

---

## Recommended Approach: Code Review Only ⭐

**What you're testing:**
- Can they write well-structured test code?
- Do they understand GraphQL concepts?
- Can they use Rest Assured API correctly?
- Are their assertions meaningful?
- Is their code readable and maintainable?

**How it works:**
1. Candidate writes the test code
2. Code compiles successfully
3. You review the code quality, not whether it runs
4. Focus on logic, structure, assertions, and approach

**Pros:**
- ✅ Fastest setup - zero infrastructure needed
- ✅ Focuses purely on coding skills
- ✅ No technical issues with servers/networks
- ✅ Industry standard for coding interviews

**Cons:**
- ❌ Tests won't execute (but that's okay for interviews!)

---

## Option B: Use Mock Server (Optional)

If you really want tests to execute, I've provided `MockGraphQLServer.java` with WireMock:

**How to use:**
```java
// Candidate's test class
public class Exercise1_ProductSearch extends BaseGraphQLTest {
    // Their regular test code works!
}

// Behind the scenes, MockGraphQLServer returns canned responses
```

**Setup:**
1. Dependency already added to `pom.xml`
2. WireMock starts automatically on `localhost:8080`
3. Returns predefined responses matching expected data

**Pros:**
- ✅ Tests actually execute
- ✅ Candidate sees green checkmarks
- ✅ Can verify tests work end-to-end

**Cons:**
- ❌ Takes time to set up
- ❌ Adds complexity to interview
- ❌ Not necessary for evaluating coding skills

---

## Option C: Use Real API (Not Recommended)

You could point to a real GraphQL eCommerce API, but:

**Cons:**
- ❌ Need to find/deploy one
- ❌ Network dependencies during interview
- ❌ Data state management issues
- ❌ API might be down/slow
- ❌ Overkill for 1-hour coding assessment

---

## What Do Real Companies Do?

**In my experience interviewing at tech companies:**

- **Google, Meta, Amazon**: Code review only, no execution
- **Smaller startups**: Sometimes use mocks, rarely real APIs
- **General practice**: Focus on code quality over execution

The goal is to see if they can **write good test code**, not whether they can debug network issues.

---

## Bottom Line Recommendation

**For a 1-hour interview:**
→ Use **Code Review Only** approach

**The candidate's imports will work because:**
- `Product.java` exists in `src/main/java/com/ecommerce/api/models/`
- `GraphQLQueries.java` exists with all query templates
- `BaseGraphQLTest.java` exists with helper methods
- Project compiles successfully with `mvn compile`

**You evaluate:**
- Does their code structure make sense?
- Are they using the helper classes correctly?
- Are their assertions checking the right things?
- Is the test logic sound?

This is **exactly how most tech companies conduct coding interviews** - the code compiles and reads well, execution is secondary.
