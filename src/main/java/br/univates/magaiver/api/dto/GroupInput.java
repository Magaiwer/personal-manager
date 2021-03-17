package br.univates.magaiver.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Magaiver Santos
 */
@Data
public class GroupInput {

    private Long id;
    @NotBlank
    private String name;
    private String description;
}
