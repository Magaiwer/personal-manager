package br.univates.magaiver.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Magaiver Santos
 */
@Getter
@Setter
public class UserStatusDTO {
    @NotNull
    private boolean enabled;
}
