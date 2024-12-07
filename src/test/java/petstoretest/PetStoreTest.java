package petstoretest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is created as part of the following challenge:
 * 
 * You have 3 days to complete the following challenge:
 * Visit the URL: https://petstore.swagger.io/#/
 * This page provides Swagger documentation for a pet store.
 * 
 * Using an API automation framework, build a regression suite
 * based on the Swagger documentation for the pet endpoint.
 * 
 * Provide a README file explaining how to run your code.
 * 
 * Requirements:
 * - Ensure all necessary validations are performed on the responses.
 * - Once completed, push your code to a feature branch (not the main branch).
 *   The naming convention for the feature branch should be:
 *   feature/<YourName-CBATest>
 * 
 * Extra Points:
 * - Run your tests in a CI environment that can be accessed by anyone.
 */
@Slf4j
public class PetStoreTest {

    public static final String baseUrl = "https://petstore.swagger.io/v2";
    
    @BeforeAll
    public static void setup() {
        baseURI = baseUrl;
    }

    @Test
    public void testAddPetValidInput() {
        log.info("Testing addPet endpoint valid input");

        String requestBody = """
                {
                    "name": "doggie",
                    "photoUrls": [
                        "string"
                    ]
                }
                """;

        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/pet")
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/pet.json"))
            .body("name", equalTo("doggie"))
            .body("photoUrls[0]", equalTo("string"))
            .body("photoUrls.size()", equalTo(1))
            .body("id", notNullValue());
    }

    @Test
    public void testAddPetInvalidInput() {
        log.info("Testing addPet endpoint invalid input");

        String requestBody = "";

        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/pet")
        .then()
            .statusCode(405)
            .contentType("application/json")
            .body(matchesJsonSchemaInClasspath("schemas/error.json"))
            .body("type", equalTo("unknown"))
            .body("message", equalTo("no data"));
    }

    @Test
    public void testGetPetByValidID() {
        log.info("Testing get pet by ID endpoint");

        String requestBody = """
                {
                    "name": "doggie",
                    "photoUrls": [
                        "string1",
                        "string2"
                    ]
                }
                """;

        long petId = given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/pet")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .extract()
            .body()
            .jsonPath()
            .getLong("id"); 

        log.info("Pet ID: {}", petId);

        given()
        .when()
            .get("/pet/{petId}", petId)
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/pet.json"))
            .body("name", equalTo("doggie"))
            .body("photoUrls", equalTo(Arrays.asList("string1", "string2")))
            .body("photoUrls.size()", equalTo(2))
            .body("id", equalTo(petId));
    }

    @Test
    public void testGetPetByNonExistID() {
        log.info("Testing get pet by non-exist ID");

        given()
        .when()
            .get("/pet/{petId}", 12345)
        .then()
            .statusCode(404)
            .contentType("application/json")
            .body(matchesJsonSchemaInClasspath("schemas/error.json"))
            .body("code", equalTo(1))
            .body("type", equalTo("error"))
            .body("message", equalTo("Pet not found"));
    }

    @Test
    public void testGetPetByInvalidID() {
        log.info("Testing get pet by invalid ID");

        int invalidPetId = -1;

        given()
        .when()
            .get("/pet/{petId}", invalidPetId)
        .then()
            .statusCode(404).log().all()
            .contentType("application/json")
            .body(matchesJsonSchemaInClasspath("schemas/error.json"))
            .body("code", equalTo(404))
            .body("type", equalTo("unknown"))
            .body("message", equalTo("Invalid ID supplied"));
    }

    @Test
    public void testUploadImage() {
        log.info("Testing uploadImage endpoint");

        String requestBody = """
                {
                    "name": "doggie",
                    "photoUrls": [
                        "string"
                    ]
                }
                """;

        long petId = given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/pet")
        .then()
            .statusCode(200).log().all()
            .contentType("application/json")
            .extract()
            .body()
            .jsonPath()
            .getLong("id"); 

        log.info("Pet ID: {}", petId);

        String imageName = "doggie.png";
        String additonalMetadata = "test";
        File imageFile = new File(getClass().getClassLoader().getResource("images/" + imageName).getFile());
        
        given()
            .contentType("multipart/form-data")
            .multiPart("additionalMetadata", additonalMetadata)
            .multiPart("file", imageFile, "image/png")
        .when()
            .post("/pet/{petId}/uploadImage", petId)
        .then()
            .statusCode(200).log().all()
            .contentType("application/json")
            .body(matchesJsonSchemaInClasspath("schemas/success.json"))
            .body("code", equalTo(200))
            .body("type", equalTo("unknown"))
            .body("message", matchesPattern("additionalMetadata: " + additonalMetadata + "\\nFile uploaded to ./" + imageName + ", \\d+ bytes"));
    }

    @Test
    public void testFindPetsByStatus() {
        log.info("Testing findPetsByStatus endpoint");
        
    }

    @Test
    public void testUpdatePetWithPUT() {
        log.info("Testing updatePetWithPUT endpoint");
    }

    @Test
    public void testUpdatePetWithPOST() {
        log.info("Testing updatePetWithPOST endpoint");
    }

    @Test
    public void testDeletePet() {
        log.info("Testing deletePet endpoint");
    }
}
