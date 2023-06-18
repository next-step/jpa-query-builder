package persistence.ddl.database;

import jdbc.RowMapper;
import persistence.EntityReflectionManager;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReflectiveRowMapper<T> implements RowMapper<T> {
    private final EntityReflectionManager entityReflectionManager;
    private final Class<T> targetType;

    public ReflectiveRowMapper(Class<T> targetType) {
        this.entityReflectionManager = new EntityReflectionManager(targetType);
        this.targetType = targetType;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        T targetObject = null;
        try {
            targetObject = targetType.getDeclaredConstructor().newInstance();

            Field[] fields = entityReflectionManager.activeField();
            for (Field field : fields) {
                String columnName = entityReflectionManager.columnName(field);
                Object value = resultSet.getObject(columnName);
                field.setAccessible(true);
                field.set(targetObject, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetObject;
    }
}
