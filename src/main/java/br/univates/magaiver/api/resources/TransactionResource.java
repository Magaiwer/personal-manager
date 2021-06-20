package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.assembler.ModelMapperAssembler;
import br.univates.magaiver.api.assembler.ModelMapperDisassembler;
import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.model.input.TransactionInput;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.output.TransactionOutput;
import br.univates.magaiver.domain.model.Transaction;
import br.univates.magaiver.domain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private final ModelMapperAssembler<Transaction, TransactionOutput> modelMapperAssembler;
    private final ModelMapperDisassembler<TransactionInput, Transaction> modelMapperDisassembler;
    private final PageModelAssembler<Transaction, TransactionOutput> pageModelAssembler;

    @Override
    public TransactionOutput save(@Valid TransactionInput transactionInput) {
        Transaction transaction = modelMapperDisassembler.toDomain(transactionInput, Transaction.class);
        return modelMapperAssembler.toModel(transactionService.save(transaction), TransactionOutput.class);
    }

    @Override
    public TransactionOutput update(@PathVariable Long id, @Valid @RequestBody TransactionInput transactionInput) {
        Transaction currentTransaction = transactionService.findByIdOrElseThrow(id);
        modelMapperDisassembler.copyToDomainObject(transactionInput, currentTransaction);
        return modelMapperAssembler.toModel(transactionService.save(currentTransaction), TransactionOutput.class);
    }

    @Override
    public void delete(@PathVariable Long id) {
        transactionService.delete(id);
    }

    @Override
    public PageModel<TransactionOutput> findAll(Pageable pageable) {
        Page<Transaction> pageModel = transactionService.findAll(pageable);
        return pageModelAssembler.toCollectionPageModel(pageModel, TransactionOutput.class);
    }

    @Override
    public TransactionOutput findById(@PathVariable Long id) {
        return modelMapperAssembler.toModel(transactionService.findByIdOrElseThrow(id), TransactionOutput.class);
    }
}