package jdbc;

import persistence.sql.metadata.Column;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityMapper<T> implements RowMapper<T>{
    private final Class<T> clazz;

    public EntityMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        if(!resultSet.next()) {
            return null;
        }

        try {
            T entity = clazz.getDeclaredConstructor().newInstance();

            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                Column column = new Column(field);

                if(column.isTransient()) {
                    continue;
                }

                field.setAccessible(true);
                field.set(entity, resultSet.getObject(column.getName()));
            }

            return entity;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
