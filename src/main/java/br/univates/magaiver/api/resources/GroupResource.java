package br.univates.magaiver.api.resources;


import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.input.GroupInput;
import br.univates.magaiver.api.model.output.GroupOutput;
import br.univates.magaiver.domain.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static br.univates.magaiver.api.singleton.MapperSingleton.GROUP_MAPPER;

@RestController
@RequestMapping(value = "/group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupResource implements BaseResource<GroupOutput, GroupInput> {
    private final GroupService groupService;

    @Override
    //@CheckPermission.Group.CanSave
    public GroupOutput save(@Valid @RequestBody GroupInput groupInput) {
        return groupService.save(groupInput);
    }

    @Override
    public GroupOutput update(@PathVariable Long id, @Valid @RequestBody GroupInput groupInput) {
        groupInput.setId(id);
        return groupService.update(groupInput);
    }

    @Override
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }

    @Override
    public PageModel<GroupOutput> findAll(Pageable pageable) {
        return groupService.findAll(pageable);
    }

    @Override
    public GroupOutput findById(@PathVariable Long id) {
        return GROUP_MAPPER.toModel(groupService.findByIdOrElseThrow(id));
    }
}
