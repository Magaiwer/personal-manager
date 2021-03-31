package br.univates.magaiver.api.dto;

import br.univates.magaiver.domain.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Magaiver Santos
 */

@Data
@Builder
@JsonSerialize
public class TransactionInput {
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private TransactionType transactionType;

    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotNull
    private Long categoryId;
    private boolean enabled;
}
