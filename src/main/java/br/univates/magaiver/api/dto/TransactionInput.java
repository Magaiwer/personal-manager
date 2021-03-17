package br.univates.magaiver.api.dto;

import br.univates.magaiver.domain.model.Category;
import br.univates.magaiver.domain.model.TransactionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Magaiver Santos
 */

@Data
public class TransactionInput {
    private Long id;
    @NotBlank
    private String name;
    private TransactionType transactionType;
    @NotBlank
    private BigDecimal amount;
    private LocalDateTime date;
    @NotBlank
    private Category category;
    private boolean enabled;
}
