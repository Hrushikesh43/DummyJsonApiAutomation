package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class PostRequestUsingOrgJson {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";  // Mock API for Testing
    }

    @Test
    public void testPostRequest() {
        // Create JSON Request Body Using JSONObject
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "morpheus");
        requestBody.put("job", "leader");

        // Send POST Request
        Response response = given()
                .header("Content-Type", "application/json")  // Set request as JSON
                .body(requestBody.toString())  // ✅ Convert JSONObject to String
                .when()
                .post("/users")  // API Endpoint
                .then()
                .statusCode(201)  // Validate Status Code
                .extract().response();

        // Print Response
        System.out.println("Response Body: " + response.asString());

        // Extract JSON Data
        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");
        int statusCode = response.getStatusCode();


        // Assertions
        Assert.assertEquals(name, "morpheus", "Name does not match!");
        Assert.assertEquals(job, "leader", "Job does not match!");
        Assert.assertNotNull(response.jsonPath().getString("id"), "ID is null!");
    }
}
