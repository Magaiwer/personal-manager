package br.univates.magaiver.api.assembler;

import br.univates.magaiver.api.model.input.UserInput;
import br.univates.magaiver.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDisassembler {

    private final ModelMapper modelMapper;

    public User toDomain(UserInput originDTO, Class<User> userDestination) {
        return modelMapper.map(originDTO, userDestination);
    }

    public void copyToDomainObject(UserInput userInput, User user) {
        user.setGroups(null);
        modelMapper.map(userInput, user);
    }
}
