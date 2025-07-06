package tests;

import dto.CreateUserRequest;
import dto.CreateUserResponse;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest extends TestBase {
    RequestSpecification requestSpecification = given().contentType(ContentType.JSON)
            .header("x-api-key", "reqres-free-v1");
    ResponseSpecification responseSpecification = expect().statusCode(201)
            .contentType(ContentType.JSON)
            .body("name", notNullValue())
            .body("job", notNullValue())
            .body("id", notNullValue())
            .body("createdAt", notNullValue());


    @Test
    public void createUserTest() {
        String job = "QA";
        String name = "John Doe";

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName(name);
        createUserRequest.setJob(job);

        CreateUserResponse createUserResponse = given().spec(requestSpecification)
                .body(createUserRequest)
                .when()
                .post("users")
                .as(CreateUserResponse.class);

        Assertions.assertEquals(job, createUserResponse.getJob());
        Assertions.assertEquals(name, createUserResponse.getName());
    }

    @Test
    public void createUserTestWithResponseSpec() {
        String job = "Developer";
        String name = "Jane Smith";

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName(name);
        createUserRequest.setJob(job);

        CreateUserResponse createUserResponse = given().spec(requestSpecification)
                .body(createUserRequest)
                .when()
                .post("users")
                .then()
                .spec(responseSpecification)
                .extract()
                .as(CreateUserResponse.class);

        Assertions.assertEquals(job, createUserResponse.getJob());
        Assertions.assertEquals(name, createUserResponse.getName());
    }
}
