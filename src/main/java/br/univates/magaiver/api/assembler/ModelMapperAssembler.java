package br.univates.magaiver.api.assembler;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Magaiver Santos
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModelMapperAssembler<T, S> {

    private final ModelMapper modelMapper;

    public S toModel(T domain, Class<S> objectInput) {
        return modelMapper.map(domain, objectInput);
    }

    public List<S> toCollectionModel(Collection<T> domains, Class<S> objectInput) {
        return domains.stream()
                .map(domain -> toModel(domain, objectInput))
                .collect(Collectors.toList());
    }
}