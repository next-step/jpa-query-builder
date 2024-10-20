package persistence.sql.dml;

import jakarta.persistence.Column;
import java.lang.reflect.Field;

public class ValidateInsertValue {

    public ValidateInsertValue(Field field, Object value) {
        if (field.isAnnotationPresent(Column.class)) {
            validateNotNull(field, value);
        }
    }

    private void validateNotNull(Field field, Object value) {
        Column column = field.getAnnotation(Column.class);
        if (!column.nullable() && value == null) {
            throw new IllegalArgumentException("Non-nullable field '" + field.getName() + "' is null");
        }
    }

}
