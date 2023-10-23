package persistence.entity;

import jdbc.RowMapper;
import persistence.sql.entity.EntityColumn;
import persistence.sql.entity.EntityData;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleRowMapper<T> implements RowMapper<T> {

    private final Class<T> entityClass;

    public SimpleRowMapper(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        T entity;
        try {
            entity = entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("엔티티 객체를 생성하는데 오류가 발생하였습니다.", e);
        }

        resultSet.next();

        EntityData entityData = new EntityData(entityClass);
        for (EntityColumn column : entityData.getEntityColumns().getEntityColumnList()) {
            setValues(resultSet, entity, column);
        }

        return entity;
    }

    /**
     * 엔티티의 각각 필드에 값 설정
     *
     * @param resultSet
     * @param entity
     * @param entityColumn
     */
    private void setValues(ResultSet resultSet, T entity, EntityColumn entityColumn) {
        Field field = entityColumn.getField();
        field.setAccessible(true);
        try {
            field.set(entity, getValueFromResultSet(entityColumn, resultSet));
        } catch (Exception e) {
            throw new RuntimeException("엔티티 필드의 값을 설정하는데 오류가 발생하였습니다.", e);
        }
    }

    /**
     * ResultSet에서 타입과 필드 이름을 통해 값을 가져옴
     *
     * @param entityColumn
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private Object getValueFromResultSet(EntityColumn entityColumn, ResultSet resultSet) throws SQLException {
        if (entityColumn.getType() == Long.class) {
            return resultSet.getLong(entityColumn.getColumnName());
        } else if (entityColumn.getType() == String.class) {
            return resultSet.getString(entityColumn.getColumnName());
        } else if (entityColumn.getType() == Integer.class) {
            return resultSet.getInt(entityColumn.getColumnName());
        } else {
            throw new RuntimeException("정의되지 않은 타입입니다.");
        }
    }


}
