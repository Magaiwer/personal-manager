package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.filter.TransactionFilter;
import br.univates.magaiver.api.model.input.TransactionInput;
import br.univates.magaiver.api.model.output.TransactionOutput;
import br.univates.magaiver.domain.model.Transaction;
import br.univates.magaiver.domain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Magaiver Santos
 */
@RestController
@RequestMapping(value = "/transaction")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionResource implements BaseResource<TransactionOutput, TransactionInput> {

    private final TransactionService transactionService;

    @Override
    public TransactionOutput save(@Valid TransactionInput transactionInput) {
        return transactionService.save(transactionInput);
    }

    @Override
    public TransactionOutput update(@PathVariable Long id, @Valid @RequestBody TransactionInput transactionInput) {
        transactionInput.setId(id);
        return transactionService.update(transactionInput);
    }

    @Override
    public void delete(@PathVariable Long id) {
        transactionService.delete(id);
    }

    @Override
    public PageModel<TransactionOutput> findAll(Pageable pageable) {
        return transactionService.findAll(pageable);
    }

    @GetMapping("/filter")
    public PageModel<TransactionOutput> filter(@RequestBody TransactionFilter filter, Pageable pageable) {
        return transactionService.filter(filter, pageable);
    }

    @Override
    public TransactionOutput findById(@PathVariable Long id) {
        return transactionService.findByIdOrElseThrow(id);
    }
}