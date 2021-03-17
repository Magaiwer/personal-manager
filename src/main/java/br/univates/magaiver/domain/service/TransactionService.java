package br.univates.magaiver.domain.service;

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

    private static final String MSG_ENTITY_IN_USE_KEY = "transaction.already.use";

    @Modifying
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public Page<Transaction> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Transaction findByIdOrElseThrow(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(MSG_NOT_FOUND, id)));
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
