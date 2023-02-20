package br.univates.magaiver.api.model.mapper;

import br.univates.magaiver.api.model.input.AccountInput;
import br.univates.magaiver.api.model.output.AccountOutput;
import br.univates.magaiver.domain.model.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    AccountOutput toModel(Account account);

    Account toDomain(AccountInput accountInput);

}
