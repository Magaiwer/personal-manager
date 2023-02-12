package br.univates.magaiver.api.model.mapper;

import br.univates.magaiver.api.model.input.TransactionInput;
import br.univates.magaiver.api.model.output.TransactionOutput;
import br.univates.magaiver.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "categoryId", source = "category.id")
    TransactionOutput toModel(Transaction transaction);

    @Mapping(source = "accountId", target = "account.id")
    @Mapping(source = "categoryId", target = "category.id")
    Transaction toDomain(TransactionInput transactionInput);

}
