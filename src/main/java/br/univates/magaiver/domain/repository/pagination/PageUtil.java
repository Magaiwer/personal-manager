package br.univates.magaiver.domain.repository.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public class  PageUtil<T> {
    @PersistenceContext
    private EntityManager manager;

    public void prepare(TypedQuery<T> criteria, Pageable pageable) {
        if (pageable.isPaged()) {
            int maxRecordsForPage = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int firstRecord = currentPage * maxRecordsForPage;
            criteria.setFirstResult(firstRecord);
            criteria.setMaxResults(maxRecordsForPage);

        }

        if (pageable.getSort().isSorted()) {

        }
    }


    public TypedQuery<T> prepararOrdem(CriteriaQuery<T> query, Root<T> fromEntity, Pageable pageable) {
        Sort sort = pageable.getSort();

        if (sort != Sort.unsorted()) {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            Sort.Order sortOrder = sort.iterator().next();
            String property = sortOrder.getProperty();

            Order order = sortOrder.isAscending() ? builder.asc(fromEntity.get(property))
                    : builder.desc(fromEntity.get(property));
            query.orderBy(order);
        }

        return manager.createQuery(query);
    }
}