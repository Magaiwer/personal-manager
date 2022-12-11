package br.univates.magaiver.domain.service;

import br.univates.magaiver.api.assembler.ModelMapperAssembler;
import br.univates.magaiver.api.assembler.ModelMapperDisassembler;
import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.filter.TransactionFilter;
import br.univates.magaiver.api.model.input.TransactionInput;
import br.univates.magaiver.api.model.output.TransactionOutput;
import br.univates.magaiver.core.property.Messages;
import br.univates.magaiver.domain.exception.EntityAlreadyInUseException;
import br.univates.magaiver.domain.exception.EntityNotFoundException;
import br.univates.magaiver.domain.model.Transaction;
import br.univates.magaiver.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

/**
 * @author Magaiver Santos
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

    private static final String MSG_NOT_FOUND = "Transação de código %d não encontrada";
    private final TransactionRepository transactionRepository;
    private final Messages messages;

    private final ModelMapperAssembler<Transaction, TransactionOutput> modelMapperAssembler;
    private final ModelMapperDisassembler<TransactionInput, Transaction> modelMapperDisassembler;
    private final PageModelAssembler<Transaction, TransactionOutput> pageModelAssembler;

    private static final String MSG_ENTITY_IN_USE_KEY = "transaction.already.use";

    @Modifying
    public TransactionOutput save(TransactionInput transactionInput) {
        Transaction transaction = modelMapperDisassembler.toDomain(transactionInput, Transaction.class);
        return modelMapperAssembler.toModel(transactionRepository.save(transaction), TransactionOutput.class);
    }

    public TransactionOutput update(TransactionInput transactionInput) {
        findByIdOrElseThrow(transactionInput.getId());
        return this.save(transactionInput);
    }

    @Transactional(readOnly = true)
    public PageModel<TransactionOutput> findAll(Pageable pageable) {
        Page<Transaction> pageModel = transactionRepository.findAll(pageable);
        return pageModelAssembler.toCollectionPageModel(pageModel, TransactionOutput.class);
    }

    @Transactional(readOnly = true)
    public PageModel<TransactionOutput> filter(TransactionFilter filter, Pageable pageable) {
        Page<Transaction> pageModel = transactionRepository.filter(filter, pageable);
        return pageModelAssembler.toCollectionPageModel(pageModel, TransactionOutput.class);
    }

    @Transactional(readOnly = true)
    public TransactionOutput findByIdOrElseThrow(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(MSG_NOT_FOUND, id)));
        return modelMapperAssembler.toModel(transaction, TransactionOutput.class);
    }

    @Transactional
    public void delete(Long id) {
        try {
            transactionRepository.deleteById(id);
            transactionRepository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException(format(MSG_NOT_FOUND, id));
        } catch (DataIntegrityViolationException e) {
            String message = messages.getMessage(MSG_ENTITY_IN_USE_KEY);
            throw new EntityAlreadyInUseException(format(message, id));
        }
    }
}
