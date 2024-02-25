package persistence.entity;

import jdbc.RowMapper;
import persistence.sql.domain.Column;
import persistence.sql.domain.Table;

import java.lang.reflect.Field;
import java.util.List;

public class RowMapperFactory {

    private RowMapperFactory() {
        throw new IllegalArgumentException("RowMapperFactory is a utility class");
    }

    public static <T> RowMapper<T> create(Class<T> clazz) {
        return resultSet -> {
            try {
                T instance = clazz.getDeclaredConstructor().newInstance();
                Table table = Table.from(clazz);
                List<Column> columns = table.getColumns();
                for (Column column : columns) {
                    Field field = column.getField();
                    field.setAccessible(true);
                    field.set(instance, resultSet.getObject(column.getName()));
                }
                return instance;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create instance of: " + clazz.getName(), e);
            }
        };
    }
}
