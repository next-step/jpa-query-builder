package jdbc;

import persistence.sql.ColumnUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class RowMapperImpl<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public RowMapperImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        try {
            final T instance = clazz.getDeclaredConstructor().newInstance();
            final List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                    .filter(ColumnUtils::includeColumn)
                    .toList();

            for (Field field : fields) {
                field.setAccessible(true);
                field.set(instance, resultSet.getObject(ColumnUtils.name(field)));
            }

            return instance;
        } catch (Exception e) {
            throw new SQLException("Error mapping row", e);
        }
    }
}
