package br.univates.magaiver.domain.repository.helpler.transaction;

import br.univates.magaiver.api.model.filter.TransactionFilter;
import br.univates.magaiver.domain.model.Transaction;
import br.univates.magaiver.domain.repository.pagination.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Transaction> filter(TransactionFilter filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> fromTransaction = query.from(Transaction.class);
        fromTransaction.fetch("category");

        Predicate[] filters = addFilter(filter, fromTransaction);
        query.select(fromTransaction).where(filters);

        TypedQuery<Transaction> transactionTypedQuery = entityManager.createQuery(query);

        new PageUtil<Transaction>().prepare(transactionTypedQuery, pageable);
        List<Transaction> result = transactionTypedQuery.getResultList();

        return new PageImpl<>(result, pageable, result.size());
    }

    private Predicate[] addFilter(TransactionFilter filter, Root<Transaction> transactionEntity) {
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        if (filter != null) {
            if (filter.getCategoryId() != null) {
                predicates.add(builder.equal(transactionEntity.get("category").get("id"), filter.getCategoryId()));
            }

            if (filter.getMonthDate() != null) {
                LocalDate startOfTheMonth = filter.getMonthDate().with(TemporalAdjusters.firstDayOfMonth());
                LocalDate endOfTheMonth = filter.getMonthDate().with(TemporalAdjusters.lastDayOfMonth());
                predicates.add(builder.between(transactionEntity.get("date"), startOfTheMonth, endOfTheMonth));
            }

            if (filter.getType() != null) {
                predicates.add(builder.equal(transactionEntity.get("transactionType"), filter.getType()));
            }
        }
        return predicates.toArray(new Predicate[0]);
    }
}
