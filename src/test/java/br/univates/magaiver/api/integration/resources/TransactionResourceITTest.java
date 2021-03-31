package br.univates.magaiver.api.integration.resources;

import br.univates.magaiver.api.dto.CategoryInput;
import br.univates.magaiver.api.dto.TransactionInput;
import br.univates.magaiver.domain.model.TransactionType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.springframework.http.HttpStatus;

import javax.annotation.Priority;
import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

/**
 * @author Magaiver Santos
 */

public class TransactionResourceITTest extends AbstractITTest {

    public static final long TRANSACTION_ID_1 = 1L;
    public static final long TRANSACTION_ID_2 = 2L;
    public static final int TRANSACTION_ID_NOT_FOUND = 9999;

    @Before
    public void setup() {
       // RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.basePath = "/transaction";
        authenticate();
        given().spec(requestSpecification);
    }

    @After
    public void reset() {
        RestAssured.reset();
    }

    @Test
    public void shouldBeReturnStatusCode200WhenFindAllTransactions() {
        given()
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
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(transaction)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldBeReturnStatusCode200WhenUpdateTransactionWithSuccess() {
        TransactionInput transaction = dataPreparing();
        transaction.setName("Luz");
        transaction.setId(TRANSACTION_ID_2);
        transaction.setAmount(new BigDecimal("220.00"));

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("id", transaction.getId())
                .body(transaction)
        .when()
                .put("/{id}")
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @Order(1)
    public void shouldBeReturnStatusCode200WhenFindById1() {
        given()
                .pathParam("id", TRANSACTION_ID_2)
                .accept(ContentType.JSON)
        .when()
                .get("/{id}")
        .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldBeReturnStatusCode404WhenIdNotFound() {
        given()
                .pathParam("id", TRANSACTION_ID_NOT_FOUND)
                .accept(ContentType.JSON)
        .when()
                .get("/{id}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Order(2)
    public void shouldBeReturnStatusCode204WhenDeleteWithSuccess() {
        given()
                .pathParam("id", TRANSACTION_ID_1)
                .accept(ContentType.JSON)
        .when()
                .delete("/{id}")
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }


    private TransactionInput dataPreparing() {
        CategoryInput categoryInput = CategoryInput.builder().id(1L).build();

        return TransactionInput.builder()
                .amount(new BigDecimal("140.00"))
                .date(LocalDate.now())
                .name("Internet")
                .categoryId(categoryInput.getId())
                .enabled(true)
                .transactionType(TransactionType.EXPENSE)
                .build();
    }
}
