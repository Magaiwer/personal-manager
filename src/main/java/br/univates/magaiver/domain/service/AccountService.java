package br.univates.magaiver.domain.service;

import br.univates.magaiver.api.assembler.PageModelAssembler;
import br.univates.magaiver.api.model.PageModel;
import br.univates.magaiver.api.model.input.AccountInput;
import br.univates.magaiver.api.model.output.AccountOutput;
import br.univates.magaiver.domain.model.Account;
import br.univates.magaiver.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.univates.magaiver.api.singleton.MapperSingleton.ACCOUNT_MAPPER;

/**
 * @author Magaiver Santos
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService extends AbstractService<Account> {

    private static final String MSG_NOT_FOUND_KEY = "account.not.found";
    private static final String MSG_ENTITY_IN_USE_KEY = "account.already.use";
    private final AccountRepository accountRepository;
    private final PageModelAssembler<Account, AccountOutput> pageModelAssembler;

    @Transactional
    public AccountOutput save(AccountInput accountInput) {
        Account account = ACCOUNT_MAPPER.toDomain(accountInput);
        return ACCOUNT_MAPPER.toModel(accountRepository.save(account));
    }

    @Modifying
    public AccountOutput update(AccountInput accountInput) {
        findByIdOrElseThrow(accountInput.getId());
        return this.save(accountInput);
    }

    @Transactional(readOnly = true)
    public PageModel<AccountOutput> findAll(Pageable pageable) {
        Page<Account> pageModel = super.findAll(pageable, accountRepository);
        return pageModelAssembler.toCollectionPageModel(pageModel, AccountOutput.class);
    }

    @Transactional(readOnly = true)
    public AccountOutput findByIdOrElseThrow(Long id) {
        return ACCOUNT_MAPPER.toModel(super.findByIdOrElseThrow(accountRepository, id, MSG_NOT_FOUND_KEY));
    }

    @Transactional
    public void delete(Long id) {
        super.delete(accountRepository, id, MSG_NOT_FOUND_KEY, MSG_ENTITY_IN_USE_KEY);
    }

}
