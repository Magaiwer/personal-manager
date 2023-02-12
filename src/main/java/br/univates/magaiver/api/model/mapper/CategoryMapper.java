package br.univates.magaiver.api.model.mapper;

import br.univates.magaiver.api.model.input.CategoryInput;
import br.univates.magaiver.api.model.input.UserInput;
import br.univates.magaiver.api.model.output.CategoryOutput;
import br.univates.magaiver.api.model.output.UserGroupOutput;
import br.univates.magaiver.api.model.output.UserOutput;
import br.univates.magaiver.domain.model.Category;
import br.univates.magaiver.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    CategoryOutput toModel(Category category);
    Category toDomain(CategoryInput categoryInput);

}
