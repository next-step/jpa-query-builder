package persistence.sql.ddl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;

public class ColumnConstraint {

    private static final String NOT_NULL_CONSTRAINT = "NOT NULL";
    private final Field field;

    public ColumnConstraint(Field field) {
        this.field = field;
    }

    public String getConstraint() {
        if (!field.getAnnotation(Column.class).nullable()) {
            return NOT_NULL_CONSTRAINT;
        }
        return "";
    }

}
