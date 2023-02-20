package br.univates.magaiver.api.resources;

import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.input.AccountInput;
import br.univates.magaiver.api.model.output.AccountOutput;
import br.univates.magaiver.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Magaiver Santos
 */
@RestController
@RequestMapping(value = "/account")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin
public class AccountResource implements BaseResource<AccountOutput, AccountInput> {

    private final AccountService accountService;


    @Override
    public AccountOutput save(@Valid AccountInput accountInput) {
        return accountService.save(accountInput);
    }

    @Override
    public AccountOutput update(@PathVariable Long id, @Valid @RequestBody AccountInput accountInput) {
        accountInput.setId(id);
        return accountService.update(accountInput);
    }

    @Override
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

    @Override
    public PageModel<AccountOutput> findAll(Pageable pageable) {
        return accountService.findAll(pageable);
    }

    @Override
    public AccountOutput findById(@PathVariable Long id) {
        return accountService.findByIdOrElseThrow(id);
    }
}