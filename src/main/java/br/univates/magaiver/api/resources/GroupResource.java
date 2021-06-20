package br.univates.magaiver.api.resources;


import br.univates.magaiver.api.assembler.ModelMapperAssembler;
import br.univates.magaiver.api.assembler.ModelMapperDisassembler;
import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.model.input.GroupInput;
import br.univates.magaiver.api.model.output.GroupOutput;
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
public class GroupResource implements BaseResource<GroupOutput, GroupInput> {
    private final GroupService groupService;
    private final ModelMapperAssembler<Group, GroupOutput> modelMapperAssembler;
    private final ModelMapperDisassembler<GroupInput, Group> modelMapperDisassembler;
    private final PageModelAssembler<Group, GroupOutput> pageModelAssembler;

    @Override
    //@CheckPermission.Group.CanSave
    public GroupOutput save(@Valid @RequestBody GroupInput groupInput) {
        Group group = modelMapperDisassembler.toDomain(groupInput, Group.class);
        return modelMapperAssembler.toModel(groupService.save(group), GroupOutput.class);
    }

    @Override
    public GroupOutput update(@PathVariable Long id, @Valid @RequestBody GroupInput groupInput) {
        Group currentGroup = groupService.findByIdOrElseThrow(id);
        modelMapperDisassembler.copyToDomainObject(groupInput, currentGroup);
        return modelMapperAssembler.toModel(groupService.save(currentGroup), GroupOutput.class);
    }

    @Override
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }

    @Override
    public PageModel<GroupOutput> findAll(Pageable pageable) {
        return pageModelAssembler.toCollectionPageModel(groupService.findAll(pageable), GroupOutput.class);
    }

    @Override
    public GroupOutput findById(@PathVariable Long id) {
        return modelMapperAssembler.toModel(groupService.findByIdOrElseThrow(id), GroupOutput.class);
    }
}
