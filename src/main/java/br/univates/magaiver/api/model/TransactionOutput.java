package br.univates.magaiver.api.model;

import br.univates.magaiver.domain.model.TransactionType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Magaiver Santos
 */

@Data
@JsonDeserialize
public class TransactionOutput {
    private Long id;
    private String name;
    private TransactionType transactionType;
    private BigDecimal amount;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    private CategoryOutput category;
    private boolean enabled;
}
