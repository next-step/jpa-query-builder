package database.mapper;

import jdbc.RowMapper;
import utils.EntityAnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ResultMapper<T> implements RowMapper<T> {

    private Class<T> entityClass;

    public ResultMapper(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T mapRow(final ResultSet resultSet) throws SQLException {
        resultSet.next();
        try {
            T entity = entityClass.getConstructor().newInstance();

            Arrays.stream(entityClass.getDeclaredFields()).forEach(field -> {
                try {
                    Class<?> type = field.getType();
                    field.setAccessible(true);
                    if (type.equals(String.class)) {
                        String string = resultSet.getString(EntityAnnotationUtils.parseColumnName(field));
                        field.set(entity, string);
                    }


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
