package br.univates.magaiver.api.assembler;

import br.univates.magaiver.api.dto.UserDTO;
import br.univates.magaiver.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDisassembler {

    private final ModelMapper modelMapper;

    public User toDomain(UserDTO originDTO, Class<User> userDestination) {
        return modelMapper.map(originDTO, userDestination);
    }

    public void copyToDomainObject(UserDTO userDTO, User user) {
        user.setGroups(null);
        modelMapper.map(userDTO, user);
    }
}
