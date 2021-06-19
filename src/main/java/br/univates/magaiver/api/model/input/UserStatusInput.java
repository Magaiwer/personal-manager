package br.univates.magaiver.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Magaiver Santos
 */
@Getter
@Setter
public class UserStatusInput {
    @NotNull
    private boolean enabled;
}
