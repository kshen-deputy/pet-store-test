# Pet Store API Test Suite

This project contains an automated test suite for the [Swagger Pet Store API](https://petstore.swagger.io/). It tests all the pet endpoints using REST Assured and JUnit 5.

## Test Coverage

The test suite covers the following endpoints:

### Pet Endpoints
- POST `/pet` - Add a new pet
- PUT `/pet` - Update an existing pet
- POST `/pet/{petId}` - Updates a pet in the store with form data
- GET `/pet/{petId}` - Find pet by ID
- POST `/pet/{petId}/uploadImage` - Uploads an image
- GET `/pet/findByStatus` - Finds pets by status
- DELETE `/pet/{petId}` - Deletes a pet

### Known Issues

1. **Get Pet by Invalid ID Test**: The API returns "Pet not found" (404) instead of "Invalid ID supplied" (400) when an invalid ID is provided.
2. **Find Pets by Invalid Status Test**: The API returns status code 200 instead of the expected 400 for invalid status values.
3. **Find Pets by Valid Status Test**: Some pets in the 'sold' status have no name, causing schema validation failures.
4. **Upload Image Test**: The uploaded image URL is not returned in the response, making it difficult to verify the upload through the GET endpoint.

## Requirements

- Java 17
- Gradle 8.11.1 (wrapper included)
- Internet connection (to access the Pet Store API)

## Project Structure

```
src/
├── test/
│   ├── java/
│   │   └── petstoretest/
│   │       └── PetStoreTest.java    # Test cases
│   └── resources/
│       ├── images/                  # Test images
│       └── schemas/                 # JSON schemas for validation
│           ├── error.json
│           ├── pet.json
│           ├── success.json
│           └── swagger.json
```

## Running Tests

Unix/macOS:
```bash
./gradlew test
```

Windows:
```cmd
gradlew.bat test
```

## Test Reports

After running the tests, you can find the test reports at:
```
build/reports/tests/test/index.html
```

## CI

This project uses GitHub Actions for continuous integration. The workflow:
- Runs on every push to main and pull requests
- Uses Ubuntu latest with Java 17
- Executes all tests using Gradle
- Uploads test reports as artifacts

You can find the test results in the Actions tab of the repository after each run. The workflow configuration is in `.github/workflows/gradle-test.yml`.

## Dependencies

- REST Assured: API testing framework
- JUnit 5: Testing framework
- Loogging: SLF4J and Lombok

## Note

The Gradle wrapper (`gradlew` for Unix/macOS and `gradlew.bat` for Windows) is included in the project, so you don't need to install Gradle separately.
