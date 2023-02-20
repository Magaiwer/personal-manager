package br.univates.magaiver.domain.service;

import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.filter.TransactionFilter;
import br.univates.magaiver.api.model.input.TransactionInput;
import br.univates.magaiver.api.model.output.TransactionOutput;
import br.univates.magaiver.domain.model.Transaction;
import br.univates.magaiver.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.univates.magaiver.api.singleton.MapperSingleton.TRANSACTION_MAPPER;

/**
 * @author Magaiver Santos
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService extends AbstractService<Transaction> {

    private static final String MSG_NOT_FOUND_KEY = "transaction.not.found";
    private static final String MSG_ENTITY_IN_USE_KEY = "transaction.already.use";
    private final TransactionRepository transactionRepository;
    private final PageModelAssembler<Transaction, TransactionOutput> pageModelAssembler;

    @Transactional
    public TransactionOutput save(TransactionInput transactionInput) {
        Transaction transaction = TRANSACTION_MAPPER.toDomain(transactionInput);
        return TRANSACTION_MAPPER.toModel(transactionRepository.save(transaction));
    }

    public TransactionOutput update(TransactionInput transactionInput) {
        findByIdOrElseThrow(transactionInput.getId());
        return this.save(transactionInput);
    }

    @Transactional(readOnly = true)
    public PageModel<TransactionOutput> findAll(Pageable pageable) {
        Page<Transaction> pageModel = super.findAll(pageable, transactionRepository);
        return pageModelAssembler.toCollectionPageModel(pageModel, TransactionOutput.class);
    }

    @Transactional(readOnly = true)
    public PageModel<TransactionOutput> filter(TransactionFilter filter, Pageable pageable) {
        Page<Transaction> pageModel = transactionRepository.filter(filter, pageable);
        return pageModelAssembler.toCollectionPageModel(pageModel, TransactionOutput.class);
    }

    @Transactional(readOnly = true)
    public TransactionOutput findByIdOrElseThrow(Long id) {
        Transaction transaction = super.findByIdOrElseThrow(transactionRepository, id, MSG_NOT_FOUND_KEY);
        return TRANSACTION_MAPPER.toModel(transaction);
    }

    @Transactional
    public void delete(Long id) {
        super.delete(transactionRepository, id, MSG_NOT_FOUND_KEY, MSG_ENTITY_IN_USE_KEY);
    }

}