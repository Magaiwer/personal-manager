package br.univates.magaiver.api.model.mapper;

import br.univates.magaiver.api.model.input.GroupInput;
import br.univates.magaiver.api.model.output.GroupOutput;
import br.univates.magaiver.domain.model.Group;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface GroupMapper {

    GroupOutput toModel(Group group);

    Group toDomain(GroupInput groupInput);

    List<GroupOutput> toCollectionModel(Set<Group> groups);

}
