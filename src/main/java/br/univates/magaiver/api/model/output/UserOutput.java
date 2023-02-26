package br.univates.magaiver.api.model.output;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Magaiver Santos
 */
@Data
public class UserOutput {
    private Long id;
    private String name;
    private String email;
    private boolean enable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
