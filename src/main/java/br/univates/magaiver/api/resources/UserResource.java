package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.assembler.ModelMapperAssembler;
import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.assembler.UserDisassembler;
import br.univates.magaiver.api.dto.UserDTO;
import br.univates.magaiver.api.dto.UserPasswordDTO;
import br.univates.magaiver.api.dto.UserStatusDTO;
import br.univates.magaiver.api.dto.UserWithPasswordDTO;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.UserGroupModel;
import br.univates.magaiver.api.model.UserModel;
import br.univates.magaiver.domain.model.User;
import br.univates.magaiver.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Magaiver Santos
 */

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserResource {
    private final UserService userService;
    private final ModelMapperAssembler<User, UserModel> modelMapperAssembler;
    private final ModelMapperAssembler<User, UserGroupModel> userGroupModelMapperAssembler;
    private final UserDisassembler userDisassembler;
    private final PageModelAssembler<User, UserModel> pageModelAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel save(@Valid @RequestBody UserWithPasswordDTO userWithPasswordDTO) {
        User user = userDisassembler.toDomain(userWithPasswordDTO, User.class);
        user = userService.save(user);
        return modelMapperAssembler.toModel(user, UserModel.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserModel update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        User currentUser = userService.findByIdOrElseThrow(id);
        userDisassembler.copyToDomainObject(userDTO, currentUser);
        return modelMapperAssembler.toModel(userService.save(currentUser), UserModel.class);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDTO userPasswordModel) {
        userService.changePassword(id, userPasswordModel.getPassword(), userPasswordModel.getCurrentPassword());
    }

    @PutMapping("/{id}/change-status")
    @ResponseStatus(HttpStatus.OK)
    public void changeStatus(@PathVariable Long id, @Valid @RequestBody UserStatusDTO userStatusDTO) {
        userService.changeUserStatus(id, userStatusDTO.isEnabled() );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping
    public PageModel<UserModel> findAll(Pageable pageable) {
        Page<User> user = userService.findAll(pageable);
        return pageModelAssembler.toCollectionPageModel(user, UserModel.class);
    }

    @GetMapping("/{id}")
    public UserGroupModel findById(@PathVariable Long id) {
        User user = userService.findCompleteByIdOrElseThrow(id);
        return userGroupModelMapperAssembler.toModel(user, UserGroupModel.class);
    }
}
