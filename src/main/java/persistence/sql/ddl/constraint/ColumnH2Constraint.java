package persistence.sql.ddl.constraint;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnH2Constraint implements H2Constraint {

    private static final String NOT_NULL_CONSTRAINT_QUERY = "NOT NULL";

    private final String constraintQuery;

    public ColumnH2Constraint(Field field) {
        this.constraintQuery = generateConstraintQuery(field);
    }

    @Override
    public String generateConstraintQuery(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            return getNullable(column);
        }
        return EMPTY_STRING;
    }

    private String getNullable(Column column) {
        if (!column.nullable()) {
            return NOT_NULL_CONSTRAINT_QUERY;
        }

        return EMPTY_STRING;
    }

    @Override
    public String getConstraintQuery() {
        return constraintQuery;
    }
}
