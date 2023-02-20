package br.univates.magaiver.api.model.output;

import lombok.Data;

/**
 * @author Magaiver Santos
 */

@Data
public class AccountOutput {
    private Long id;
    private String name;
    private String description;
}
