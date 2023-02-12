package br.univates.magaiver.api.model.mapper;

import br.univates.magaiver.api.model.input.UserInput;
import br.univates.magaiver.api.model.output.UserGroupOutput;
import br.univates.magaiver.api.model.output.UserOutput;
import br.univates.magaiver.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserOutput toModel(User user);
    UserGroupOutput toModelGroup(User user);
    User toDomain(UserInput userInput);

}
