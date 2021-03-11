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

    public S toModel(T domain, Class<S> objectDTO) {
        return modelMapper.map(domain, objectDTO);
    }

    public List<S> toCollectionModel(Collection<T> domains, Class<S> objectDTO) {
        return domains.stream()
                .map(domain -> toModel(domain, objectDTO))
                .collect(Collectors.toList());
    }
}