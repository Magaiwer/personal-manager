package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.input.UserInput;
import br.univates.magaiver.api.model.input.UserPasswordInput;
import br.univates.magaiver.api.model.input.UserStatusInput;
import br.univates.magaiver.api.model.input.UserWithPasswordInput;
import br.univates.magaiver.api.model.output.UserGroupOutput;
import br.univates.magaiver.api.model.output.UserOutput;
import br.univates.magaiver.domain.model.User;
import br.univates.magaiver.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.univates.magaiver.api.singleton.MapperSingleton.USER_MAPPER;

/**
 * @author Magaiver Santos
 */

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserResource {
    private final UserService userService;
    private final PageModelAssembler<User, UserOutput> pageModelAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutput save(@Valid @RequestBody UserWithPasswordInput userWithPasswordDTO) {
        User user = USER_MAPPER.toDomain(userWithPasswordDTO);
        return USER_MAPPER.toModel(userService.save(user));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserOutput update(@PathVariable Long id, @Valid @RequestBody UserInput userInput) {
        User currentUser = userService.findByIdOrElseThrow(id);
        currentUser = USER_MAPPER.toDomain(userInput);
        return USER_MAPPER.toModel(userService.save(currentUser));
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordInput userPasswordModel) {
        userService.changePassword(id, userPasswordModel.getPassword(), userPasswordModel.getCurrentPassword());
    }

    @PutMapping("/{id}/change-status")
    @ResponseStatus(HttpStatus.OK)
    public void changeStatus(@PathVariable Long id, @Valid @RequestBody UserStatusInput userStatusInput) {
        userService.changeUserStatus(id, userStatusInput.isEnabled());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping
    public PageModel<UserOutput> findAll(Pageable pageable) {
        Page<User> user = userService.findAll(pageable);
        return pageModelAssembler.toCollectionPageModel(user, UserOutput.class);
    }

    @GetMapping("/{id}")
    public UserGroupOutput findById(@PathVariable Long id) {
        User user = userService.findCompleteByIdOrElseThrow(id);
        return USER_MAPPER.toModelGroup(user);
    }
}
