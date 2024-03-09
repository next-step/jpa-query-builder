package persistence.sql.model;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class ColumnFactory {
    public static Optional<Column> createColumn(Field field) {
        boolean isPKColumn = false;
        var isTransient = false;
        for(var annotation : field.getAnnotations()) {
            if (annotation.annotationType().equals(Id.class)) isPKColumn = true;
            if (annotation.annotationType().equals(Transient.class)) isTransient = true;
        }

        if (isTransient) return Optional.empty();
        if (isPKColumn) return Optional.of(PKColumn.from(field));
        return Optional.of(SimpleColumn.from(field));
    }
}
