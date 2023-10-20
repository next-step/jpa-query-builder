package jdbc;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.exception.NotFoundException;
import persistence.meta.ColumnType;
import persistence.meta.EntityColumn;
import persistence.meta.EntityMeta;


public class EntityRowMapper<T> implements RowMapper<T> {

    private final Class<T> tClass;

    public EntityRowMapper(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            return generateEntity(tClass, resultSet);
        } catch (Exception e) {
            throw new NotFoundException("엔티티 멥핑이 실패 하였습니다." + e.getMessage());
        }
    }

    /**
     * 엔티티와 DB Result Set을 맵핑해준다.
     * */
    private T generateEntity(Class<T> tClass, ResultSet resultSet) throws Exception {
        T t = tClass.getDeclaredConstructor().newInstance();
        final EntityMeta entityMeta = new EntityMeta(t.getClass());

        for (EntityColumn entityColumn : entityMeta.getEntityColumns()) {
            final Object resultSetColumn = getResultSetColumn(resultSet, entityColumn);
            final Field field = tClass.getDeclaredField(entityColumn.getFiledName());
            field.setAccessible(true);
            field.set(t, resultSetColumn);
        }

        return t;
    }

    private Object getResultSetColumn(ResultSet resultSet, EntityColumn column) {
        final ColumnType columType = column.getColumType();
        try {
            return getTypeValue(resultSet, column, columType);
        } catch (SQLException e) {
            throw new NotFoundException("값을 찾지 못 하였습니다.");
        }
    }

    private static Object getTypeValue(ResultSet resultSet, EntityColumn column, ColumnType columType)
            throws SQLException {
        if (columType.isBigInt()) {
            return resultSet.getLong(column.getName());
        }
        if (columType.isVarchar()) {
            return resultSet.getString(column.getName());
        }
        if (columType.isInteger()) {
            return resultSet.getInt(column.getName());
        }
        return null;
    }


}
