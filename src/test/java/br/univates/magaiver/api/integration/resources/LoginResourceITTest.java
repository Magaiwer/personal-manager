package br.univates.magaiver.api.integration.resources;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.*;

/**
 * @author Magaiver Santos
 */
public class LoginResourceITTest extends AbstractITTest {

    @Before
    public void setup() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.basePath = "/login";
        authenticate();
    }

    @Test
    public void shouldBeReturn401WhenLoginFail() {
        given()
                .accept(ContentType.JSON)
                .body(mockWrongUser())
        .when()
            .post()
        .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void shouldBeReturn200WhenSuccessLogin() {
        given()
                .contentType("application/json")
                .body(mockUser())
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldBeReturnTokenWhenSuccessLogin() {
        String token = given()
                .contentType("application/json")
                .body(mockUser())
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .header(HEADER_STRING);

        Assert.assertNotNull(token);
    }

}
