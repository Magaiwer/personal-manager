package br.univates.magaiver.api.model;

import br.univates.magaiver.domain.model.Group;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Magaiver Santos
 */
@Getter @Setter
public class UserGroupModel extends UserModel {

    @JsonIgnoreProperties(value = {"permissions"})
    private Set<Group> groups;
}
