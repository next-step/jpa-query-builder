package persistence.sql.ddl.constraints.builder;

import jakarta.persistence.Column;
import java.lang.reflect.Field;

public interface ConstraintsBuilder {
    default String getConstraintsFrom(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return getConstraintsFrom(field.getAnnotation(Column.class));
        }

        return "";
    }
    default String getConstraintsFrom(Column columnAnnotation) {
        return "";
    }
}
