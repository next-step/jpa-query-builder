package persistence.sql.ddl.constraints.builder;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import persistence.sql.ddl.common.StringConstants;

public class NotNullConstraintsBuilder implements ConstraintsBuilder {

    private static final String NOT_NULL_CONSTRAINTS = "NOT NULL";

    @Override
    public String getConstraintsFrom(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return getConstraintsFrom(field.getAnnotation(Column.class));
        }

        return StringConstants.EMPTY_STRING;
    }

    protected String getConstraintsFrom(Column columnAnnotation) {
        if (!columnAnnotation.nullable()) {
            return NOT_NULL_CONSTRAINTS;
        }

        return StringConstants.EMPTY_STRING;
    }
}
