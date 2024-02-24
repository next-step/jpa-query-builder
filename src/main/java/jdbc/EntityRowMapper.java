package jdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import persistence.sql.meta.Columns;

public class EntityRowMapper<T> implements RowMapper {

    private final Class<T> clazz;

    public EntityRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            Columns columns = Columns.from(clazz.getDeclaredFields());
            columns.getColumns()
                .forEach(column -> column.setFieldValue(t, ResultSetColumnReader.map(resultSet, column.getColumnName(),
                    column.getType())));
            return t;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
