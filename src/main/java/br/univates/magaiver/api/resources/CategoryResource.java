package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.input.CategoryInput;
import br.univates.magaiver.api.model.output.CategoryOutput;
import br.univates.magaiver.domain.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.univates.magaiver.api.singleton.MapperSingleton.CATEGORY_MAPPER;

/**
 * @author Magaiver Santos
 */
@RestController
@RequestMapping(value = "/category")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin
public class CategoryResource implements BaseResource<CategoryOutput, CategoryInput> {

    private final CategoryService categoryService;


    @Override
    public CategoryOutput save(@Valid CategoryInput categoryInput) {
        return categoryService.save(categoryInput);
    }

    @Override
    public CategoryOutput update(@PathVariable Long id, @Valid @RequestBody CategoryInput categoryInput) {
        categoryInput.setId(id);
        return categoryService.update(categoryInput);
    }

    @Override
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @Override
    public PageModel<CategoryOutput> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @Override
    public CategoryOutput findById(@PathVariable Long id) {
        return CATEGORY_MAPPER.toModel(categoryService.findByIdOrElseThrow(id));
    }
}