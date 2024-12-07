package petstoretest;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

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
    public void testAddPet() {
        log.info("Testing addPet endpoint");
    }

    @Test
    public void testUploadImage() {
        log.info("Testing uploadImage endpoint");
    }

    @Test
    public void testFindPetsByStatus() {
        log.info("Testing findPetsByStatus endpoint");
    }

    @Test
    public void testFindPetsByID() {
        log.info("Testing findPetsByID endpoint");
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
