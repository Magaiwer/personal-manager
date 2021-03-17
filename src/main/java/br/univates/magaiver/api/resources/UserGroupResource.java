package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.assembler.ModelMapperAssembler;
import br.univates.magaiver.api.model.GroupOutput;
import br.univates.magaiver.domain.model.Group;
import br.univates.magaiver.domain.model.User;
import br.univates.magaiver.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Magaiver Santos
 */

@RestController
@RequestMapping(value = "/user/{userId}/groups")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserGroupResource {
    private final UserService userService;
    private final ModelMapperAssembler<Group, GroupOutput> modelMapperAssembler;

    @GetMapping
    public List<GroupOutput> findAll(@PathVariable Long userId) {
        User user = userService.findByIdOrElseThrow(userId);
        return modelMapperAssembler.toCollectionModel(user.getGroups(), GroupOutput.class);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disconnect(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.disconnectGroup(userId, groupId);
    }

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void connect(@PathVariable Long userId, @PathVariable Long groupId) {
        userService.connectGroup(userId, groupId);
    }

}
