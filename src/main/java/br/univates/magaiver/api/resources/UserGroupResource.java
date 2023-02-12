package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.model.output.GroupOutput;
import br.univates.magaiver.domain.model.User;
import br.univates.magaiver.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.univates.magaiver.api.singleton.MapperSingleton.GROUP_MAPPER;

/**
 * @author Magaiver Santos
 */

@RestController
@RequestMapping(value = "/user/{userId}/groups")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserGroupResource {
    private final UserService userService;

    @GetMapping
    public List<GroupOutput> findAll(@PathVariable Long userId) {
        User user = userService.findByIdOrElseThrow(userId);
        return GROUP_MAPPER.toCollectionModel(user.getGroups());
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
