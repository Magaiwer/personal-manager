package br.univates.magaiver.api.model.filter;

import br.univates.magaiver.domain.model.TransactionType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonSerialize
public class TransactionFilter {
    private Long id;
    private LocalDate monthDate;
    private TransactionType type;
    private Long categoryId;
}
