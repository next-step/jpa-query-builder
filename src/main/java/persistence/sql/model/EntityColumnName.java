
package persistence.sql.model;

import jakarta.persistence.Column;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredFieldException;

import java.lang.reflect.Field;

public class EntityColumnName {

    private final String value;

    public EntityColumnName(Field field) {
        if (field == null) {
            throw new RequiredFieldException(ExceptionMessage.REQUIRED_FIELD);
        }
        this.value = makeColumnName(field);
    }

    private String makeColumnName(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }

        String columnName = field.getAnnotation(Column.class).name();
        if (columnName.isEmpty()) {
            return field.getName();
        }

        return columnName;
    }

    public String getValue() {
        return value;
    }
}
