package br.univates.magaiver.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Magaiver Santos
 */
@Getter
@Setter
public class UserWithPasswordInput extends UserInput {

    @NotBlank
    private String password;
}
