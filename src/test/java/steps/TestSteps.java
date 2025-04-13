package steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utilities.JsonSchemaValidatorUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestSteps {
    Response response;

    @Given("I have the post data")
    public void i_have_post_data() {
        baseURI = "https://jsonplaceholder.typicode.com";
    }

    @When("I send a POST request to create a new post")
    public void create_new_post() {
        response = given()
                .header("Content-type", "application/json")
                .body("{ \"title\": \"Learn API Testing\", \"body\": \"Practicing API testing with JSONPlaceholder\", \"userId\": 101 }")
                .when()
                .post("/posts");
    }

    @Then("The response should contain the correct data and match the schema")
    public void validate_create_response() {
        response.then().statusCode(201)
                .body("title", equalTo("Learn API Testing"))
                .body("body", equalTo("Practicing API testing with JSONPlaceholder"))
                .body("userId", equalTo(101));
        JsonSchemaValidatorUtil.validateSchema(response, "createPostSchema.json");
    }

    @When("I send a GET request to retrieve all posts")
    public void get_all_posts() {
        response = when().get("/posts");
    }

    @Then("The response should contain posts with valid ids and match the schema")
    public void validate_get_response() {
        response.then().statusCode(200)
                .body("id", everyItem(notNullValue()));
        JsonSchemaValidatorUtil.validateSchema(response, "getPostsSchema.json");
    }

    @When("I send a DELETE request")
    public void delete_post() {
        response = when().delete("/posts/1");
    }

    @Then("The response should indicate successful deletion and match the schema")
    public void validate_delete_response() {
        response.then().statusCode(200);
        JsonSchemaValidatorUtil.validateSchema(response, "deletePostSchema.json");
    }

    @When("I send a PUT request to update post with certain id")
    public void update_post() {
        response = given()
                .header("Content-type", "application/json")
                .body("{ \"id\": 1, \"title\": \"Updated Post Title\", \"body\": \"This is the updated body content.\", \"userId\": 99 }")
                .when()
                .put("/posts/1");
    }

    @Then("The response should contain the updated data and match the schema")
    public void validate_update_response() {
        response.then().statusCode(200)
                .body("title", equalTo("Updated Post Title"))
                .body("body", equalTo("This is the updated body content."))
                .body("userId", equalTo(99));
        JsonSchemaValidatorUtil.validateSchema(response, "updatePostSchema.json");
    }
}
