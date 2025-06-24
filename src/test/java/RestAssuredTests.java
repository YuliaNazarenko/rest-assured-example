import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class RestAssuredTests {

    public RestAssuredTests() {
        RestAssured.baseURI = "https://reqres.in";

    }

    @Test
    public void test() {
        given().log().all()
                .get("/api/users")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void addParamTest() {
        given().log().all()
                .when()
                .param("page", "2")
                .get("/api/users")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void checkHeaderTest() {
        given().contentType(ContentType.JSON).log().all()
                .when()
                .get("/api/users")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void checkBodyTest() {
        Response response =
                given().log().all()
                        .when()
                        .get("/api/users");
        System.out.println(response.prettyPrint());

        Assertions.assertTrue(response.body().asString().contains("george.bluth@reqres.in"));
    }
}
