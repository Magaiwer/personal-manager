package br.univates.magaiver.api.integration.resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
abstract class AbstractITTest {

    protected static final String HEADER_STRING = "Authorization";

    @LocalServerPort
    protected int port;

    protected void authenticate() {
        String token =
                given()
                        .contentType("application/json")
                        .body(mockUser())
                        .basePath("/")
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .header(HEADER_STRING);

        requestSpecification =
                new RequestSpecBuilder()
                        .addHeader(HEADER_STRING, token)
                        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                        .build();
    }

    protected String mockUser() {
        return "{\"email\":\"magaiwer@hotmail.com.br\", \"password\": \"1234\"}";
    }

    protected String mockWrongUser() {
        return "{\"email\":\"magaiwer@hotmail.com.br\", \"password\": \"123569\"}";
    }

}
