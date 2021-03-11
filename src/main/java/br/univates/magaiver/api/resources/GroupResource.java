package br.univates.magaiver.api.resources;


import br.univates.magaiver.api.assembler.ModelMapperAssembler;
import br.univates.magaiver.api.assembler.ModelMapperDisassembler;
import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.dto.GroupDTO;
import br.univates.magaiver.api.model.GroupModel;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.domain.model.Group;
import br.univates.magaiver.domain.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupResource implements BaseResource<GroupModel, GroupDTO> {
    private final GroupService groupService;
    private final ModelMapperAssembler<Group, GroupModel> modelMapperAssembler;
    private final ModelMapperDisassembler<GroupDTO, Group> modelMapperDisassembler;
    private final PageModelAssembler<Group, GroupModel> pageModelAssembler;

    @Override
    //@CheckPermission.Group.CanSave
    public GroupModel save(@Valid @RequestBody GroupDTO groupDTO) {
        Group group = modelMapperDisassembler.toDomain(groupDTO, Group.class);
        return modelMapperAssembler.toModel(groupService.save(group), GroupModel.class);
    }

    @Override
    //@CheckPermission.Group.CanUpdate
    public GroupModel update(@PathVariable Long id, @Valid @RequestBody GroupDTO groupDTO) {
        Group currentGroup = groupService.findByIdOrElseThrow(id);
        modelMapperDisassembler.copyToDomainObject(groupDTO, currentGroup);
        return modelMapperAssembler.toModel(groupService.save(currentGroup), GroupModel.class);
    }

    @Override
   // @CheckPermission.Group.CanDelete
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }

    @Override
    //@CheckPermission.Group.CanView
    public PageModel<GroupModel> findAll(Pageable pageable) {
        return pageModelAssembler.toCollectionPageModel(groupService.findAll(pageable), GroupModel.class);
    }

    @Override
    //@CheckPermission.Group.CanView
    public GroupModel findById(@PathVariable Long id) {
        return modelMapperAssembler.toModel(groupService.findByIdOrElseThrow(id), GroupModel.class);
    }
}
