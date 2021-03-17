package br.univates.magaiver.api.model;

import br.univates.magaiver.domain.model.Category;
import br.univates.magaiver.domain.model.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Magaiver Santos
 */

@Data
public class TransactionOutput {
    private Long id;
    private String name;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime date;
    private Category category;
    private boolean enabled;
}
