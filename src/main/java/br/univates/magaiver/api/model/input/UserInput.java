package br.univates.magaiver.api.model.input;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author Magaiver Santos
 */
@Data
public class UserInput {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    private boolean enable;
    private Set<GroupInput> groups;

}
