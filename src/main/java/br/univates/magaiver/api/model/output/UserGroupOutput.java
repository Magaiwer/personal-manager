package br.univates.magaiver.api.model.output;

import br.univates.magaiver.domain.model.Group;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author Magaiver Santos
 */
@Getter @Setter
public class UserGroupOutput extends UserOutput {

    @JsonIgnoreProperties(value = {"permissions"})
    private Set<Group> groups;
}
