package br.univates.magaiver.domain.service;

import br.univates.magaiver.core.property.Messages;
import br.univates.magaiver.domain.exception.EntityAlreadyInUseException;
import br.univates.magaiver.domain.exception.EntityNotFoundException;
import br.univates.magaiver.domain.model.Category;
import br.univates.magaiver.domain.repository.CategoryRepository;
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
public class CategoryService {

    private static final String MSG_NOT_FOUND = "Categoria de código %d não encontrado";
    private final CategoryRepository categoryRepository;
    private final Messages messages;

    private static final String MSG_ENTITY_IN_USE_KEY = "category.already.use";

    @Modifying
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Category findByIdOrElseThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(MSG_NOT_FOUND, id)));
    }

    @Transactional
    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
            categoryRepository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException(format(MSG_NOT_FOUND, id));
        } catch (DataIntegrityViolationException e) {
            String message = messages.getMessage(MSG_ENTITY_IN_USE_KEY);
            throw new EntityAlreadyInUseException(format(message, id));
        }
    }

}
