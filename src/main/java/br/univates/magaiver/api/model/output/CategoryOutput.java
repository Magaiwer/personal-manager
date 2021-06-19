package br.univates.magaiver.api.model.output;

import lombok.Data;
import lombok.Getter;

/**
 * @author Magaiver Santos
 */

@Data
public class CategoryOutput {
    private Long id;
    private String name;
    private String description;
    private String icon;
}
