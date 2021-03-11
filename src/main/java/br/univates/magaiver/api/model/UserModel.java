package br.univates.magaiver.api.model;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author Magaiver Santos
 */
@Data
public class UserModel {
    private Long id;
    private String name;
    private String email;
    private boolean enabled;
    private OffsetDateTime createdAt;
}
