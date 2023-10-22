package persistence.sql.schema.constraint;

import jakarta.persistence.Column;
import java.lang.reflect.Field;

public class NotNullConstraint implements Constraint {

    public static final String NOT_NULL = "NOT NULL";
    
    private final String constraint;


    public NotNullConstraint(Field field) {
        this.constraint = extractConstraint(field);
    }

    @Override
    public String getConstraint() {
        return constraint;
    }

    private String extractConstraint(Field field) {
        if (isConstraintNotNull(field)) {
            return NOT_NULL;
        }

        return "";
    }

    private static boolean isConstraintNotNull(Field field) {
        return field.isAnnotationPresent(Column.class) &&
            field.getAnnotation(Column.class).nullable() == Boolean.FALSE;
    }
}
