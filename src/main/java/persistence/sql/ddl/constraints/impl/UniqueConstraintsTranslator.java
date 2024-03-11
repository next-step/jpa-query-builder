package persistence.sql.ddl.constraints.impl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import persistence.sql.ddl.common.StringConstants;
import persistence.sql.ddl.constraints.ConstraintsTranslator;
import persistence.exception.UnsupportedFieldException;

public class UniqueConstraintsTranslator implements ConstraintsTranslator {

    private static final String UNIQUE_CONSTRAINTS = "UNIQUE";

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
            && field.getAnnotation(Column.class).unique();
    }

    protected String getConstraintsFrom(Column columnAnnotation) {
        if (columnAnnotation.unique()) {
            return UNIQUE_CONSTRAINTS;
        }

        return StringConstants.EMPTY_STRING;
    }
}
