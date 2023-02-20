package br.univates.magaiver.domain.service;

import br.univates.magaiver.core.property.Messages;
import br.univates.magaiver.domain.exception.EntityAlreadyInUseException;
import br.univates.magaiver.domain.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import static java.lang.String.format;

public abstract class AbstractService<T> {

    @Autowired
    private Messages messages;

    public void delete(JpaRepository<T, Long> repository, Long id, String keyMsgNotFound, String keyMsgInUse) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException(format(messages.getMessage(keyMsgNotFound), id));
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyInUseException(format(messages.getMessage(keyMsgInUse), id));
        }
    }

    public T findByIdOrElseThrow(JpaRepository<T, Long> repository, Long id, String keyMsgNotFound) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(messages.getMessage(keyMsgNotFound), id)));
    }

    public Page<T> findAll(Pageable pageable, JpaRepository<T, Long> repository) {
        return repository.findAll(pageable);
    }
}
