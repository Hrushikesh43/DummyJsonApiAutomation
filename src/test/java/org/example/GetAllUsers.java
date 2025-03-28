package org.example;

import Logging.Logging;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
/*
This Test case should cover :
1. Get all the users and from it get first users details
2. Use that user detail and login with that user
 */

public class GetAllUsers {

  Logger logger = LogManager.getLogger(GetAllUsers.class);
    @BeforeClass()
    public void setBaseUri()
    {
        RestAssured.baseURI = "https://dummyjson.com";
    }

    @Test(description = "This is request to get all users")
    public void getAllusers() throws InterruptedException
    {

        Response response = given().when().get("/users").then().statusCode(200).extract().response();
        String responseBody = response.asString();
        System.out.println(responseBody);
        String username = response.jsonPath().getString("users[0].username");
        String password = response.jsonPath().getString("users[0].password");
        Logging.info("Username is :" +username);
        Logging.info("Password is : "+password);
       Thread.sleep(6000);
        //Create post request to login user got from get request
        JSONObject user = new JSONObject();
        user.put("username",username);
        user.put("password", password);
        Logging.info("User request :"+user.toString());
        Response loginResponse = given().contentType("application/json").body(user.toString()).when().post("/user/login").then().statusCode(200)
                                .extract().response();
        String accessToken = loginResponse.jsonPath().getString("accessToken");
        Logging.info("The access token for login user is :"+accessToken);

        //Now use this access token to get the current authenticated user
        Response authResponse = given().header("Authorization", "Bearer "+accessToken).when().get("user/me").then().statusCode(200)
                                .extract().response();
        Logging.info(authResponse.asString());

    }


}
