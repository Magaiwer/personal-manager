/*
package br.univates.magaiver.api;

import br.univates.magaiver.api.model.filter.TransactionFilter;
import br.univates.magaiver.domain.model.Transaction;
import br.univates.magaiver.domain.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransactionRepositoryImplTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    ApplicationContext context;

    @Test
    public void shouldFilterTransactionByCategory() {

        TransactionFilter filter = TransactionFilter
                .builder()
                .categoryId(1L)
                .dtStart(LocalDate.of(2021, 1, 1))
                .dtEnd(LocalDate.now())
                .build();

        transactionRepository = context.getBean(TransactionRepository.class);

        Page<Transaction> page = transactionRepository.filter(filter, Pageable.unpaged());

        Assert.assertNotNull(page);
    }


}
*/
