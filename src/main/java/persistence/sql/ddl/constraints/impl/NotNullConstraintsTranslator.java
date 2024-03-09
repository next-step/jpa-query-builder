package persistence.sql.ddl.constraints.impl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import persistence.sql.ddl.common.StringConstants;
import persistence.sql.ddl.constraints.ConstraintsTranslator;
import persistence.sql.exception.constraints.UnsupportedFieldException;

public class NotNullConstraintsTranslator implements ConstraintsTranslator {

    private static final String NOT_NULL_CONSTRAINTS = "NOT NULL";

    @Override
    public String getConstraintsFrom(Field field) {
        if (!this.supports(field)) {
            throw new UnsupportedFieldException(field);
        }

        return getConstraintsFrom(field.getAnnotation(Column.class));
    }

    @Override
    public boolean supports(Field field) {
        return field.isAnnotationPresent(Column.class)
            && !field.getAnnotation(Column.class).nullable();
    }

    protected String getConstraintsFrom(Column columnAnnotation) {
        if (!columnAnnotation.nullable()) {
            return NOT_NULL_CONSTRAINTS;
        }

        return StringConstants.EMPTY_STRING;
    }
}
