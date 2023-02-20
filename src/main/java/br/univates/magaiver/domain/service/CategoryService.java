package br.univates.magaiver.domain.service;

import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.input.CategoryInput;
import br.univates.magaiver.api.model.output.CategoryOutput;
import br.univates.magaiver.domain.model.Category;
import br.univates.magaiver.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.univates.magaiver.api.singleton.MapperSingleton.CATEGORY_MAPPER;

/**
 * @author Magaiver Santos
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService extends AbstractService<Category> {

    private static final String MSG_NOT_FOUND_KEY = "category.not.found";
    private static final String MSG_ENTITY_IN_USE_KEY = "category.already.use";
    private final CategoryRepository categoryRepository;
    private final PageModelAssembler<Category, CategoryOutput> pageModelAssembler;


    @Transactional
    public CategoryOutput save(CategoryInput categoryInput) {
        Category category = CATEGORY_MAPPER.toDomain(categoryInput);
        return CATEGORY_MAPPER.toModel(categoryRepository.save(category));
    }

    @Modifying
    public CategoryOutput update(CategoryInput categoryInput) {
        findByIdOrElseThrow(categoryInput.getId());
        return this.save(categoryInput);
    }

    @Transactional(readOnly = true)
    public PageModel<CategoryOutput> findAll(Pageable pageable) {
        Page<Category> pageModel = super.findAll(pageable, categoryRepository);
        return pageModelAssembler.toCollectionPageModel(pageModel, CategoryOutput.class);
    }

    @Transactional(readOnly = true)
    public CategoryOutput findByIdOrElseThrow(Long id) {
        return CATEGORY_MAPPER.toModel(super.findByIdOrElseThrow(categoryRepository, id, MSG_NOT_FOUND_KEY));
    }

    @Transactional
    public void delete(Long id) {
        super.delete(categoryRepository, id, MSG_NOT_FOUND_KEY, MSG_ENTITY_IN_USE_KEY);
    }

}
