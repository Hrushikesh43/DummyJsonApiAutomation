package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.User;

import static io.restassured.RestAssured.*;

public class PostRequestUsingPOJO {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";  // Mock API for Testing
    }

    @Test
    public void testPostRequestWithPOJO() {
        // Create an object of the POJO class
        User user = new User("morpheus", "leader");

        // Send POST Request
        Response response = given()
                .header("Content-Type", "application/json")  // Set request as JSON
                .body(user)  // ✅ Pass POJO object (RestAssured converts it to JSON)
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

        //Soft Assert
        SoftAssert softAssert = new SoftAssert();

        // Assertions
        Assert.assertEquals(name, "morpheus", "Name does not match!");
        Assert.assertEquals(job, "leader", "Job does not match!");
        Assert.assertNotNull(response.jsonPath().getString("id"), "ID is null!");
    }
}
