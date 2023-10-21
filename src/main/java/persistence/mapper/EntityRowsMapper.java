package persistence.mapper;

import java.sql.ResultSet;


public class EntityRowsMapper<T> extends AbstractRowsMapper<T> {

    public EntityRowsMapper(Class<T> tClass) {
        super(tClass);
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        return mapEntity(tClass, resultSet);
    }
}
