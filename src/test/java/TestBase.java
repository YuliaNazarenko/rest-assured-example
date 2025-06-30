import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    static String url = "https://reqres.in";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = url;
        RestAssured.basePath = "api";
        //RestAssured.requestSpecification = given().accept(ContentType.ANY);
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.given()
                .header("x-api-key", "reqres-free-v1");
    }
}
