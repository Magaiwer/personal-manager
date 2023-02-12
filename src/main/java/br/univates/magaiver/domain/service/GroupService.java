package br.univates.magaiver.domain.service;

import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.input.GroupInput;
import br.univates.magaiver.api.model.output.GroupOutput;
import br.univates.magaiver.core.property.Messages;
import br.univates.magaiver.domain.exception.EntityAlreadyInUseException;
import br.univates.magaiver.domain.exception.EntityNotFoundException;
import br.univates.magaiver.domain.model.Group;
import br.univates.magaiver.domain.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.univates.magaiver.api.singleton.MapperSingleton.GROUP_MAPPER;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupService {
    private static final String MSG_NOT_FOUND = "Grupo de código %d não encontrado";
    private final GroupRepository groupRepository;
    private final PageModelAssembler<Group, GroupOutput> pageModelAssembler;
    private final Messages messages;

    private static final String MSG_ENTITY_IN_USE_KEY = "group.already.use";

    @Modifying
    public GroupOutput save(GroupInput groupInput) {
        Group group = groupRepository.save(GROUP_MAPPER.toDomain(groupInput));
        return GROUP_MAPPER.toModel(group);
    }

    public GroupOutput update(GroupInput groupInput) {
        findByIdOrElseThrow(groupInput.getId());
        return this.save(groupInput);
    }

    @Transactional(readOnly = true)
    public PageModel<GroupOutput> findAll(Pageable pageable) {
        return pageModelAssembler.toCollectionPageModel(groupRepository.findAll(pageable), GroupOutput.class);
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