
package persistence.sql.model;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class EntityColumnName {

    private final String value;

    public EntityColumnName(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("field가 존재하지 않습니다.");
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
