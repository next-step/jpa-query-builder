package orm.row_mapper;

import jdbc.RowMapper;
import orm.TableEntity;
import orm.exception.RowMapperException;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class DefaultRowMapper<T> implements RowMapper<T> {

    private final Class<T> type;

    public DefaultRowMapper(Class<T> type) {
        this.type = type;
    }

    public DefaultRowMapper(TableEntity<T> tableEntity) {
        this.type = tableEntity.getTableClass();
    }

    public T mapRow(ResultSet rs) throws RowMapperException {
        try {
            T instance = type.getDeclaredConstructor().newInstance();

            Field[] fields = type.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                String columnName = field.getName();
                Object value = rs.getObject(columnName);

                field.set(instance, value);
            }

            return instance;
        } catch (Exception e) {
            throw new RowMapperException("Failed to map row to " + type.getName(), e);
        }
    }
}

