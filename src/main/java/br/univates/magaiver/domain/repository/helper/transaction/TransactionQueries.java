package br.univates.magaiver.domain.repository.helper.transaction;

import br.univates.magaiver.api.model.filter.TransactionFilter;
import br.univates.magaiver.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionQueries {
    Page<Transaction> filter(TransactionFilter filter, Pageable pageable);
}
