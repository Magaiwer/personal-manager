package br.univates.magaiver.domain.repository;

import br.univates.magaiver.domain.model.Transaction;
import br.univates.magaiver.domain.repository.helpler.transaction.TransactionQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Magaiver Santos
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionQueries {
}
