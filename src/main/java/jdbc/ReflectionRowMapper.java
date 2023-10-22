package jdbc;

import hibernate.entity.EntityClass;
import hibernate.entity.column.EntityColumn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReflectionRowMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public ReflectionRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        return generateMappedInstance(resultSet, new EntityClass<>(clazz));
    }

    private T generateMappedInstance(ResultSet resultSet, EntityClass<T> entityClass) {
        moveToNextRow(resultSet);
        T instance = entityClass.newInstance();
        List<EntityColumn> entityColumns = entityClass.getEntityColumns();
        for (EntityColumn entityColumn : entityColumns) {
            entityColumn.assignFieldValue(instance, getResultSetColumn(resultSet, entityColumn));
        }
        return instance;
    }

    private void moveToNextRow(ResultSet resultSet) {
        try {
            resultSet.next();
        } catch (SQLException e) {
            throw new IllegalStateException("쿼리 실행 결과 추출에 문제가 발생했습니다.", e);
        }
    }

    private Object getResultSetColumn(ResultSet resultSet, EntityColumn entityColumn) {
        try {
            return resultSet.getObject(entityColumn.getFieldName());
        } catch (SQLException e) {
            throw new IllegalStateException("column 추출에 문제가 발생했습니다.", e);
        }
    }
}
