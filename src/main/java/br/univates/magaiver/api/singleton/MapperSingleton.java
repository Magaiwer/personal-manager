package br.univates.magaiver.api.singleton;

import br.univates.magaiver.api.model.mapper.CategoryMapper;
import br.univates.magaiver.api.model.mapper.GroupMapper;
import br.univates.magaiver.api.model.mapper.TransactionMapper;
import br.univates.magaiver.api.model.mapper.UserMapper;
import org.mapstruct.factory.Mappers;

public class MapperSingleton {

    public static final UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
    public static final CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);
    public static final GroupMapper GROUP_MAPPER = Mappers.getMapper(GroupMapper.class);
    public static final TransactionMapper TRANSACTION_MAPPER = Mappers.getMapper(TransactionMapper.class);
}
