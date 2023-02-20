package br.univates.magaiver.api.model.input;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Magaiver Santos
 */

@Data
@Builder
public class AccountInput {

    private Long id;
    @NotBlank
    private String name;
    private String description;
}
