package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.dto.CategoryInput;
import br.univates.magaiver.api.dto.TransactionInput;
import br.univates.magaiver.api.util.JsonUtils;
import br.univates.magaiver.domain.model.TransactionType;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

/**
 * @author Magaiver Santos
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class TransactionResourceITTest {

    private static final String HEADER_STRING = "Authorization";

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.basePath = "/transaction";
        authenticate();
    }

    @Test
    public void shouldBeReturnStatusCode200WhenFindAllTransactions() {
        given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
         .when()
                .get()
         .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldBeReturnStatusCode201WhenSaveTransaction() {
        TransactionInput transaction = dataPreparing();
        given()
                .spec(requestSpecification)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(transaction)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());

    }

    private String mockUser() {
        return "{\"email\":\"magaiwer@hotmail.com.br\", \"password\": \"1234\"}";
    }

    private void authenticate() {
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

    private TransactionInput dataPreparing() {
        CategoryInput categoryInput = CategoryInput.builder().id(1L).build();

        return TransactionInput.builder()
                .amount(new BigDecimal("140.00"))
                .date(LocalDate.now())
                .name("Internet")
                .category(categoryInput)
                .enabled(true)
                .transactionType(TransactionType.EXPENSE)
                .build();
    }


}
