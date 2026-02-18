# Quick Start Guide for Interviewer

## Pre-Interview Setup (5 minutes before candidate arrives)

### 1. Environment Preparation
- Ensure Java 11+ is installed
- Maven is installed and configured
- IDE is ready (IntelliJ IDEA or Eclipse recommended)

**IMPORTANT: You do NOT need a real GraphQL API**
- This is a code review interview, not integration testing
- Tests won't execute, but that's expected and fine
- Focus on evaluating code quality and structure
- See `API_SETUP_GUIDE.md` for details

### 2. Project Setup
```bash
# Extract the project files
cd graphql-api-interview

# Install dependencies
mvn clean install

# Verify compilation
mvn compile
```

### 3. Share with Candidate
Send them this folder structure and have them import into their IDE.

---

## During the Interview

### Opening (2 minutes)
"Today you'll be working on GraphQL API test automation for an eCommerce platform. You have three exercises of increasing complexity:

1. **Product Search** (15 min) - Basic GraphQL query testing
2. **Cart Operations** (20 min) - Mutation testing with state management  
3. **Order Workflow** (15 min, BONUS) - End-to-end testing

The project structure includes:
- Helper classes for GraphQL queries
- Base test class with utility methods
- Model classes for API responses

Feel free to ask questions and think out loud. Ready?"

### What You're Looking For

#### Exercise 1 (Must Complete)
✅ Understands GraphQL query structure  
✅ Can use Rest Assured effectively  
✅ Writes meaningful assertions (not just null checks)  
✅ Handles both success and error cases  

**If struggling**: Point them to GraphQLQueries class and BaseGraphQLTest helpers

#### Exercise 2 (Must Complete)
✅ Manages test data isolation (unique user IDs)  
✅ Chains multiple API calls correctly  
✅ Validates state changes (quantities, totals)  
✅ Tests mutations (add, update, remove)  

**If struggling**: Ask "How would you ensure tests don't interfere with each other?"

#### Exercise 3 (Bonus)
✅ Designs multi-step workflows  
✅ Validates complex nested structures  
✅ Thinks about realistic scenarios  

**Only if time permits**

---

## Time Management

| Time | Activity |
|------|----------|
| 0-5 min | Setup & familiarization |
| 5-20 min | Exercise 1: Product Search |
| 20-40 min | Exercise 2: Cart Operations |
| 40-55 min | Exercise 3 (optional) OR code review |
| 55-60 min | Wrap-up discussion |

**If running behind schedule**: Skip Exercise 3, focus on quality of first two

---

## Red Flags 🚩
- Cannot understand basic GraphQL structure after explanation
- Doesn't validate response data beyond null checks
- No consideration for test isolation
- Cannot debug simple issues
- Writes untestable or unmaintainable code

## Green Flags ✅
- Asks about test data strategy early
- Considers edge cases unprompted
- Explains their reasoning
- Clean, readable code
- Uses helper methods appropriately
- Thinks about maintainability

---

## Key Discussion Questions

After coding, ask 2-3 of these:

**Technical Depth:**
- "How would you handle GraphQL schema changes in your test suite?"
- "What test data management strategy would you use in production?"
- "How does GraphQL error handling differ from REST?"

**Practical Experience:**
- "How would you integrate these into CI/CD?"
- "What metrics would you track for test health?"
- "How would you handle flaky tests?"

**Advanced (if time):**
- "How would you test GraphQL subscriptions?"
- "How would you implement parallel execution?"

---

## Evaluation Checklist

### Code Quality (/30)
- [ ] Clean, readable code
- [ ] Proper naming conventions  
- [ ] Good organization
- [ ] Uses helpers appropriately
- [ ] Error handling

### Technical Skills (/40)
- [ ] GraphQL understanding
- [ ] Rest Assured proficiency
- [ ] Quality assertions
- [ ] Test design

### Problem Solving (/20)
- [ ] Debugging approach
- [ ] Handles ambiguity
- [ ] Edge case consideration
- [ ] Completion speed

### Communication (/10)
- [ ] Explains thinking
- [ ] Asks clarifying questions

**Total: /100**

**Decision:**
- 80+: Strong Hire ⭐
- 60-79: Hire ✅
- 40-59: Borderline 🤔
- <40: No Hire ❌

---

## Common Questions & Answers

**Q: "Is there a real API endpoint?"**  
A: No, this is a coding exercise. Focus on writing the test logic. In a real scenario, you'd point to a test environment or use mock servers.

**Q: "Can I use Google?"**  
A: Yes, but we want to see your thought process. Documentation lookup is fine.

**Q: "Should I write the tests to actually run?"**  
A: Focus on the test logic and structure. Compilation is good, but we care more about your approach.

**Q: "What if I don't finish?"**  
A: That's okay! We care about quality over speed. Complete what you can well.

---

## After the Interview

1. Review their code against the solution in `solutions/` folder
2. Fill out evaluation form
3. Provide specific feedback examples
4. Make hiring recommendation

**Remember:** Look for potential and problem-solving ability, not just completion. A candidate who completes 70% with clean code and good reasoning beats one who rushes through 100% with poor quality.
