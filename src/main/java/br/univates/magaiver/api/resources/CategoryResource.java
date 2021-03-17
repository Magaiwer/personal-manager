package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.assembler.ModelMapperAssembler;
import br.univates.magaiver.api.assembler.ModelMapperDisassembler;
import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.dto.CategoryInput;
import br.univates.magaiver.api.model.CategoryOutput;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.domain.model.Category;
import br.univates.magaiver.domain.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Magaiver Santos
 */
@RestController
@RequestMapping(value = "/category")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin
public class CategoryResource implements BaseResource<CategoryOutput, CategoryInput> {

    private final CategoryService categoryService;
    private final ModelMapperAssembler<Category, CategoryOutput> modelMapperAssembler;
    private final ModelMapperDisassembler<CategoryInput, Category> modelMapperDisassembler;
    private final PageModelAssembler<Category, CategoryOutput> pageModelAssembler;

    @Override
    public CategoryOutput save(@Valid CategoryInput categoryInput) {
        Category category = modelMapperDisassembler.toDomain(categoryInput, Category.class);
        return modelMapperAssembler.toModel(categoryService.save(category), CategoryOutput.class);
    }

    @Override
    public CategoryOutput update(@PathVariable Long id, @Valid @RequestBody CategoryInput categoryInput) {
        Category currentCategory = categoryService.findByIdOrElseThrow(id);
        modelMapperDisassembler.copyToDomainObject(categoryInput, currentCategory);
        return modelMapperAssembler.toModel(categoryService.save(currentCategory), CategoryOutput.class);
    }

    @Override
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @Override
    public PageModel<CategoryOutput> findAll(Pageable pageable) {
        return pageModelAssembler.toCollectionPageModel(categoryService.findAll(pageable), CategoryOutput.class);
    }

    @Override
    public CategoryOutput findById(@PathVariable Long id) {
        return modelMapperAssembler.toModel(categoryService.findByIdOrElseThrow(id), CategoryOutput.class);
    }
}