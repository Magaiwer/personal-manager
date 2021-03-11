package br.univates.magaiver.api.assembler;

import br.univates.magaiver.api.model.PageModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @author Magaiver Santos
 * <p>
 * convert page br.univates.magaiver.domain in a page model
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class PageModelAssembler<T, S> {
    private final ModelMapper modelMapper;

    public PageModel<S> toCollectionPageModel(Page<T> page, Class<S> type) {
        PageModel<S> pageModel = new PageModel<>();

        pageModel.setContent(
                page.getContent().stream()
                        .map(object -> modelMapper.map(object, type))
                        .collect(Collectors.toList())
        );

        pageModel.setNumber(page.getNumber());
        pageModel.setSize(page.getSize());
        pageModel.setTotalElements(page.getTotalElements());
        pageModel.setTotalPages(page.getTotalPages());
        return pageModel;
    }
}
