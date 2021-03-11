package br.univates.magaiver.domain.service;

import br.univates.magaiver.core.property.Messages;
import br.univates.magaiver.domain.exception.*;
import br.univates.magaiver.domain.model.Group;
import br.univates.magaiver.domain.model.User;
import br.univates.magaiver.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static java.lang.String.format;

/**
 * @author Magaiver Santos
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;
    private final Messages messages;
    private final GroupService groupService;

    private static final String MSG_ENTITY_IN_USE_KEY = "user.already.use";

    @Modifying
    public User save(User user) {
        Optional<User> userExisting = userRepository.findByEmail(user.getEmail());
        validateEmailExists(userExisting.orElse(null), user);
        validateEmailChanges(userExisting.orElse(null), user);
        requiredPassword(user);
        encodePassword(user);

        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(userExisting.get().getPassword());
        }
        return userRepository.save(user);
    }

    @Transactional
    public void changePassword(Long userId, String password, String currentPassword) {
/*        User user = findByIdOrElseThrow(userId);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BusinessException("Senha atual não confere com a senha do usuário");
        }

        user.setPassword(passwordEncoder.encode(password));*/
    }

    @Transactional
    public void changeUserStatus(Long userId, boolean status) {
        User user = findByIdOrElseThrow(userId);
        user.setEnabled(status);
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User findByIdOrElseThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public User findCompleteByIdOrElseThrow(Long id) {
        return userRepository.findByWithGroups(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Modifying
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            String message = messages.getMessage(MSG_ENTITY_IN_USE_KEY);
            throw new EntityAlreadyInUseException(format(message, id));
        }
    }

    @Transactional
    public void disconnectGroup(Long userId, Long groupId) {
        User user = findByIdOrElseThrow(userId);
        user.removeGroup(groupId);
    }

    @Transactional
    public void connectGroup(Long userId, Long groupId) {
        User user = findByIdOrElseThrow(userId);
        Group group = groupService.findByIdOrElseThrow(groupId);
        user.addGroup(group);
    }

    private void validateEmailExists(User userExisting, User user) {
        if (userExisting != null && user.isNew()) {
            throw new EmailAlreadyRegisteredException(user.getEmail());
        }
    }

    private void validateEmailChanges(User userExisting, User user) {
        if (userExisting == null && !user.isNew()) {
            throw new ForbiddenExchangeEmail();
        }
    }

    private void requiredPassword(User user) {
        if (StringUtils.isEmpty(user.getPassword()) && user.isNew()) {
            throw new RequiredPasswordException();
        }
    }

    private void encodePassword(User user) {
        if (user.isNew() || !StringUtils.isEmpty(user.getPassword())) {
            //  user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        }
    }
}
