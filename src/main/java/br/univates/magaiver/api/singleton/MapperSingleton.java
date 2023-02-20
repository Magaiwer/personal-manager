package br.univates.magaiver.api.singleton;

import br.univates.magaiver.api.model.mapper.*;
import org.mapstruct.factory.Mappers;

public class MapperSingleton {

    public static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
    public static final CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);
    public static final AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);
    public static final GroupMapper GROUP_MAPPER = Mappers.getMapper(GroupMapper.class);
    public static final TransactionMapper TRANSACTION_MAPPER = Mappers.getMapper(TransactionMapper.class);
}
