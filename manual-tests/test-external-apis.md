# Manual Tests (External APIs)

## Environment

- Postman (for making API calls)

## Instruction

Tester should set the following fields before or after performing tests. Tester should time their test session, which should include the time to prepare the test environment. If there is any ambiguity in the test document or suggestions for new tests, please write down in the `Comment/Suggestion` field.

- Tester: Not set
- Test date: Not set
- Time taken: Not set
- Comment/Suggestion (optional): Not set

Tester should perform each test and write the test result in the `Result` field. The test result should be "OK" if the `Action` is accurate and the output exactly matches `Expected Output`. Otherwise, put "FAIL:" and write down the problem.

## Test Cases

### Mod Update Checker

#### Get Version From Latest Release Endpoint
- Action: Use Postman to perform GET https://api.github.com/repos/Charzard4261/wikitools/releases/latest
- Expected Output:
    - The field `tag_name` has the value `"v2.6.6"`
- Result: Not set
