package br.univates.magaiver.api.model.output;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author Magaiver Santos
 */
@Data
public class UserOutput {
    private Long id;
    private String name;
    private String email;
    private boolean enabled;
    private OffsetDateTime createdAt;
}
