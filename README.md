# QA Automation Portfolio: Selenium UI Tests + Postman API Tests

A hands-on testing project built while learning software testing. It covers **UI automation with Selenium and Java** and **API testing with Postman**, and also includes the manual test cases the automation is based on.

**Author:** Arrian Maharana, Software Testing fresher, Bengaluru

Both applications used here are public practice sites made for testing. No real user data is involved.

| Part | Practice application | What is tested |
|------|----------------------|----------------|
| UI automation | [SauceDemo](https://www.saucedemo.com) (online store) | Login, products, sorting, cart, checkout |
| API testing | [Restful-Booker](https://restful-booker.herokuapp.com) (booking API) | Auth token, create, read, update, partial update, delete |

## Tech stack

- Java 11+
- Selenium WebDriver 4
- TestNG
- Maven
- Postman (and newman for the command line)
- GitHub Actions (optional CI)

## What is covered

### UI tests (14 automated tests)

| Area | Tests |
|------|-------|
| Login | Valid login, wrong password, locked out user, empty username, empty password, logout |
| Products | Six products listed, cart badge updates on add and remove, sort by name Z to A, sort by price low to high |
| Cart and checkout | Cart shows added products, full purchase end to end, checkout without first name shows an error |

Framework features:

- **Page Object Model**: locators and page actions live in `pages/`, test logic stays in `tests/`
- **Explicit waits**: no `Thread.sleep`
- **Screenshot on failure**: saved to `target/screenshots/`
- **Configurable**: browser (Chrome, Firefox, Edge) and headless mode from the command line
- **Independent tests**: a fresh browser for every test

### API tests (11 requests in the Postman collection)

| Request | What it checks |
|---------|----------------|
| Health check | Status 201 on `/ping` |
| Create token (valid and bad credentials) | Token returned, and the error reason for bad credentials |
| Get all booking IDs | Status 200, non-empty array |
| Create booking | Status 200, response matches the request, saves `bookingId` |
| Get booking by ID | Status 200, field types and values |
| Update without token | Status 403 Forbidden (negative test) |
| Update (PUT) | Whole booking replaced |
| Partial update (PATCH) | Only the sent fields change |
| Delete booking | Status 201 |
| Get deleted booking | Status 404 |

Postman features used: collections and folders, environment variables, request chaining (the token and booking ID are saved and reused), and test scripts with assertions on status code, body and response time.

## Project structure

```
qa-automation-portfolio/
├── pom.xml                      Maven dependencies and test setup
├── testng.xml                   Test suite definition
├── src/test/
│   ├── java/com/qa/portfolio/
│   │   ├── base/BaseTest.java           Browser start and close
│   │   ├── pages/                       Page objects
│   │   ├── tests/                       Test classes
│   │   └── utils/                       Config, driver factory, listener, test data
│   └── resources/config.properties      Default browser and URL
├── api-testing/
│   ├── RestfulBooker.postman_collection.json
│   └── RestfulBooker.postman_environment.json
├── docs/
│   ├── manual-test-cases.md             Manual test cases linked to the automated tests
│   └── bug-report-template.md
└── .github/workflows/tests.yml          Runs both test sets on GitHub
```

## How to run the UI tests

You need JDK 11 or newer, Maven and Google Chrome.

```bash
mvn clean test                        # Chrome, visible browser
mvn clean test -Dheadless=true        # no browser window
mvn clean test -Dbrowser=firefox      # Firefox (Edge also works)
```

Results are in `target/surefire-reports/` (open `index.html`). Failure screenshots are in `target/screenshots/`.

## How to run the API tests

**In Postman**

1. Import both files from `api-testing/`.
2. Select the environment **Restful-Booker - Practice** (top right).
3. Open the collection, click **Run**, and run all requests in order. They depend on each other.

**From the command line with newman**

```bash
npm install -g newman
newman run api-testing/RestfulBooker.postman_collection.json -e api-testing/RestfulBooker.postman_environment.json
```

## Notes

- The practice sites are shared public services. If a test fails because the site is slow or down, run it again before treating it as a bug.
- Restful-Booker answers some errors in unusual ways (for example, bad login returns 200 with a reason message, and delete returns 201). The tests are written to match what the API really does, and the Postman scripts include comments where this happens.

## What I learned

- Turning manual test cases into automated tests with Selenium and TestNG
- Structuring a framework with the Page Object Model
- Using explicit waits to make UI tests stable
- Validating APIs: methods, status codes, response bodies and authentication
- Chaining API requests with variables

## Next steps

- Add Rest Assured so API tests also run from Java
- Add an HTML report such as Extent Reports
- Add data-driven tests with TestNG `@DataProvider`
