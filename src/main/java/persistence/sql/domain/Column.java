package persistence.sql.domain;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public interface Column {
    String getName();

    DataType getType();

    Field getField();

    static Column from(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return IdColumn.from(field);
        }
        return FieldColumn.from(field);
    }

    boolean isId();

    boolean isNullable();

    boolean isAutoIncrementId();
}
