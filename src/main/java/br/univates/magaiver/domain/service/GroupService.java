package br.univates.magaiver.domain.service;

import br.univates.magaiver.core.property.Messages;
import br.univates.magaiver.domain.exception.EntityAlreadyInUseException;
import br.univates.magaiver.domain.exception.EntityNotFoundException;
import br.univates.magaiver.domain.model.Group;
import br.univates.magaiver.domain.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupService {
    private static final String MSG_NOT_FOUND = "Grupo de código %d não encontrado";
    private final GroupRepository groupRepository;
    private final Messages messages;

    private static final String MSG_ENTITY_IN_USE_KEY = "group.already.use";

    @Modifying
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    public Page<Group> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Group findByIdOrElseThrow(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(MSG_NOT_FOUND, id)));
    }

    @Transactional
    public void delete(Long id) {
        try {
            groupRepository.deleteById(id);
            groupRepository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException(format(MSG_NOT_FOUND, id));
        } catch (DataIntegrityViolationException e) {
            String message = messages.getMessage(MSG_ENTITY_IN_USE_KEY);
            throw new EntityAlreadyInUseException(format(message, id));
        }
    }

    @Transactional
    public boolean disconnectPermission(Long permissionId, Long groupId) {
        Group group = findByIdOrElseThrow(groupId);
        return group.removePermission(permissionId);
    }
}