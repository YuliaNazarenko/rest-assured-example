import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class PetStoreTest {
    static String uriPetStore = "https://petstore.swagger.io/v2";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = uriPetStore;
        RestAssured.basePath = "pet";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @Order(1)
    public void createPet() {
        String requestBody = """
                {
                    "id": 123456,
                    "name": "myDoggie",
                    "status": "available"
                }
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200);
    }


    @Test
    @Order(3)
    public void getPetById() {
        RestAssured.given()
                .pathParam("petID", 123456)
                .when()
                .get("/{petID}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void getNonExistentPet() {
        RestAssured.given()
                .pathParam("petID", 10000011)
                .when()
                .get("/{petID}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(4)
    public void updatePet() {
        String requestBody = """
                {
                    "id": 123456,
                    "name": "myDoggie",
                    "status": "sold"
                }
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("status", equalTo("sold"));
    }

    @Test
    @Order(5)
    public void deletePetById() {
        RestAssured.given()
                .pathParam("petID", 123456)
                .when()
                .delete("/{petID}")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    public void deleteNonExistentPet() {
        RestAssured.given()
                .pathParam("petID", 10000011)
                .when()
                .delete("/{petID}")
                .then()
                .statusCode(404);
    }
}
