package org.example;

import BasePage.BaseTest;
import Logging.Logging;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
/*
This Test case should cover :
1. Get all the users and from it get first users details
2. Use that user detail and login with that user
3. When login with the user you get the response and contains auth token use that token to
get current authenticated user
 */

public class GetAllUsers extends BaseTest {


    private String username;
    private String password;
    private String accessToken;

    @BeforeClass()
    public void setBaseUri() {
        RestAssured.baseURI = "https://dummyjson.com";
    }

    @Test(description = "This is request to get all users")
    public void getAllusers() throws InterruptedException {

        Response response = given().when().get("/users").then().statusCode(200).extract().response();
        getSoftAssert().assertEquals(response.statusCode(), 200, "Status Code Mismatch");
        String responseBody = response.asString();
        Logging.info(responseBody);
        username = response.jsonPath().getString("users[0].username");
        password = response.jsonPath().getString("users[0].password");
        Logging.info("Username is :" + username);
        Logging.info("Password is : " + password);


    }

    @Test(description = "Create post request to login user which we got from get request", dependsOnMethods = "getAllusers")
    public void loginUser() {
        //Create post request to login user got from get request
        JSONObject user = new JSONObject();
        user.put("username", username);
        user.put("password", password);
        Logging.info("User request :" + user.toString());
        Response loginResponse = given().contentType("application/json").body(user.toString()).when().post("/user/login").then().statusCode(200).extract().response();
        getSoftAssert().assertEquals(loginResponse.statusCode(), 200);
        accessToken = loginResponse.jsonPath().getString("accessToken");
        Logging.info("The access token for login user is :" + accessToken);
    }

    @Test(description = "After login we get access token in response use the token to get current authenticated user", dependsOnMethods = "loginUser")
    public void getAuthenticatedUser() {
        //Now use this access token to get the current authenticated user
        Response authResponse = given().header("Authorization", "Bearer " + accessToken).when().get("user/me").then().statusCode(200).extract().response();
        Logging.info(authResponse.asString());
    }
   @AfterMethod()
    public void assertAll()
   {
       getSoftAssert().assertAll();
   }
}
