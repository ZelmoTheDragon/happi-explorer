package endpoint;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.WebContext;

class GenderIT {

    private static final String ENDPOINT = String.join("/", WebContext.API_BASE_URL, "entity", "gender");

    GenderIT() {
    }

    @BeforeAll
    static void setup() {

    }

    @Test
    void testEndpointIsOnline() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void testFilter() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .queryParam("code[eq]", "M")
                .when()
                .get(ENDPOINT)
                .then()
                .body("size", Matchers.is(1))
                .body("data[0].id", Matchers.is(DataSet.MALE_ID))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void testFind() {
        var path = String.join("/", ENDPOINT, DataSet.FEMALE_ID);
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get(path)
                .then()
                .body("name", Matchers.is("Female"))
                .body("code", Matchers.is("F"))
                .body("description", Matchers.is("A female humain"))
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void testCreate() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(DataSet.SAMPLE_JSON_DATA)
                .when()
                .post(ENDPOINT)
                .then()
                .body("error", Matchers.is("com.github.ws.rs.explorer.service.ActionDeniedException"))
                .body("message", Matchers.is("Action type [CREATE] denied !"))
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void testUpdate() {
        var updatedData = DataSet.MALE_JSON_DATA
                .replace("A male humain", "A male humain updated");

        var path = String.join("/", ENDPOINT, DataSet.MALE_ID);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(updatedData)
                .when()
                .put(path)
                .then()
                .body("error", Matchers.is("com.github.ws.rs.explorer.service.ActionDeniedException"))
                .body("message", Matchers.is("Action type [UPDATE] denied !"))
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void testDelete() {
        var path = String.join("/", ENDPOINT, DataSet.FEMALE_ID);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .delete(path)
                .then()
                .body("error", Matchers.is("com.github.ws.rs.explorer.service.ActionDeniedException"))
                .body("message", Matchers.is("Action type [DELETE] denied !"))
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    private static final class DataSet {

        private static final String MALE_ID = "09ee5d9d-bf9b-4b5d-aad0-19117eb8da34";

        private static final String FEMALE_ID = "337ac663-48da-4a97-ad55-062a2c2ebb6d";

        private static final String MALE_JSON_DATA = """
                {
                    "name": "Male",
                    "code": "M",
                    "description": "A male humain"
                }
                """;

        private static final String FEMALE_JSON_DATA = """
                {
                    "name": "Female",
                    "code": "F",
                    "description": "A female humain"
                }
                """;

        private static final String SAMPLE_JSON_DATA = """
                {
                    "name": "Hermaphrodite",
                    "code": "H",
                    "description": "Both sex organ"
                }
                """;

    }
}
