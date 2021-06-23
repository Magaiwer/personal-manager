package br.univates.magaiver.api.integration.resources;

import br.univates.magaiver.api.model.input.CategoryInput;
import br.univates.magaiver.api.model.output.CategoryOutput;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

/**
 * @author Magaiver Santos
 */

public class CategoryResourceITTest extends AbstractITTest {

    public static final long CATEGORY_ID_1 = 1L;
    public static final long CATEGORY_ID_888 = 888L;
    public static final int CATEGORY_ID_NOT_FOUND = 9999;

    @Before
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.basePath = "/category";
        authenticate();
        given().spec(requestSpecification);
    }


    @Test
    public void shouldBeReturnStatusCode200WhenFindAllCategories() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldBeReturnStatusCode201WhenSaveCategory() {
        CategoryInput categoryInput = CategoryInput
                .builder()
                .id(CATEGORY_ID_888)
                .name("Alimentacao")
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(categoryInput)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }


    @Test
    public void shouldBeReturnTransactionUpdatedOnBodyWhenUpdateCategoryWithSuccess() {
        CategoryInput categoryInput = CategoryInput
                .builder()
                .id(CATEGORY_ID_1)
                .name("Alimentacao TESTE")
                .build();

        CategoryOutput categoryOutput = new CategoryOutput();

        categoryOutput = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("id", categoryInput.getId())
                .body(categoryInput)
                .when()
                .put("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(CategoryOutput.class);

        Assert.assertEquals(categoryInput.getName(), categoryOutput.getName());
    }

/*    @Test
    public void shouldBeReturnStatusCode200WhenFindById888() {
        given()
                .pathParam("id", CATEGORY_ID_1)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value());
    }*/

    @Test
    public void shouldBeReturnStatusCode404WhenIdNotFound() {
        given()
                .pathParam("id", CATEGORY_ID_NOT_FOUND)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldBeReturnStatusCode400WhenWrongParameter() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/category/")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
