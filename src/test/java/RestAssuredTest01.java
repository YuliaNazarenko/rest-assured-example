import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RestAssuredTest01 extends TestBase {
    @Test
    public void checkBodyAsStringContainsSubstringTest() {
        Response response =
                given()
                        .header("x-api-key","reqres-free-v1")
                        .when()
                        .get("/users");
        System.out.println(response.prettyPrint());

        Assertions.assertTrue(response.body().asString().contains("george.bluth@reqres.in"));
    }

    @Test
    public void checkBodySingleUserAsStringTest() {
        Response response =
                given()
                        .header("x-api-key","reqres-free-v1")
                        .when()
                        .get("/users/23");

        System.out.println(response.prettyPrint());

        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void checkBodySingleUserBodyJsonPathTest() {

        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("/users/2")
                .then()
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"))
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .body("data.id", is(2));

    }

    @Test
    public void checkBodySingleUserCreateTest() {

        String requestBody = """
                {
                    "name": "morpheus",
                    "job": "supervisor"
                }
                """;

        given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .body("name", equalTo("morpheus"));
    }
}
