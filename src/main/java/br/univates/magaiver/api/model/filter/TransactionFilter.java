package br.univates.magaiver.api.model.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionFilter {

    private Long id;
    private LocalDate dtStart;
    private LocalDate dtEnd;
    private Long categoryId;
}
