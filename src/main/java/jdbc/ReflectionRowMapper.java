package jdbc;

import hibernate.entity.EntityClass;
import hibernate.entity.column.EntityColumn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReflectionRowMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public ReflectionRowMapper(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(final ResultSet resultSet) throws SQLException {
        return generateMappedInstance(resultSet, new EntityClass<>(clazz));
    }

    private T generateMappedInstance(final ResultSet resultSet, final EntityClass<T> entityClass) throws SQLException {
        resultSet.next();
        T instance = entityClass.newInstance();
        List<EntityColumn> entityColumns = entityClass.getEntityColumns();
        for (EntityColumn entityColumn : entityColumns) {
            entityColumn.assignFieldValue(instance,resultSet.getObject(entityColumn.getFieldName()));
        }
        return instance;
    }
}
