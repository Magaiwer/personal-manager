package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.model.PageModel;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface BaseResource<T, S> {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    T save(@Valid @RequestBody S s);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    T update(@PathVariable Long id, @Valid @RequestBody S s);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id);

    @GetMapping
    PageModel<T> findAll(Pageable pageable);

    @GetMapping("/{id}")
    T findById(@PathVariable Long id);
}
