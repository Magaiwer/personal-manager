package br.univates.magaiver.api.assembler;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param <T> Input to disassembler from.
 * @param <S> Domain to assembler to.
 * @author Magaiver Santos
 * <p>
 * A generic Input disassembler that uses Model Mapper.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ModelMapperDisassembler<T, S> {

    private final ModelMapper modelMapper;

    public S toDomain(T originDTO, Class<S> domain) {
        return modelMapper.map(originDTO, domain);
    }

    public void copyToDomainObject(T originInput, Object destination) {
        modelMapper.map(originInput, destination);
    }
}
