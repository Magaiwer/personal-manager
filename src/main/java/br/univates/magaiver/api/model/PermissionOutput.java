package br.univates.magaiver.api.model;

import lombok.Data;

/**
 * @author Magaiver Santos
 */
@Data
public class PermissionOutput {
    private Long id;
    private String role;
    private String description;
}
