package br.univates.magaiver.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Magaiver Santos
 */
@Getter
@Setter
public class PageModel<T> {
    private List<T> content;
    private Long totalElements;
    private int totalPages;
    private int number;
    private int size;
}
