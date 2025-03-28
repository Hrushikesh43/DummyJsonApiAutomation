package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.*;

public class PostRequestTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api"; // Set Base URI
    }

    @Test
    public void testPostRequest() {
        // Create JSON Request Body using HashMap
        HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "morpheus");
        requestBody.put("job", "leader");
        // Send POST Request
        Response response = given()
                .header("Content-Type", "application/json")  // Set request as JSON
                .body(requestBody)  // Convert HashMap to JSON automatically
                .when()
                .post("/users")  // API Endpoint
                .then()
                .statusCode(201)  // Validate Status Code
                .extract().response();

        // Print Response Body
        System.out.println("Response Body: " + response.asString());

        // Extract JSON Response Data
        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");

        // Assertions
        Assert.assertEquals(name, "morpheus", "Name does not match!");
        Assert.assertEquals(job, "leader", "Job does not match!");
        Assert.assertNotNull(response.jsonPath().getString("id"), "ID is null!");
    }
}
