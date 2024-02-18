package persistence.sql.ddl.constraints.impl;

import jakarta.persistence.Column;
import persistence.sql.ddl.constraints.ConstraintsBuilder;

public class NotNullConstraintsBuilder implements ConstraintsBuilder {

    @Override
    public String getConstraintsFrom(Column columnAnnotation) {
        if (!columnAnnotation.nullable()) {
            return "NOT NULL";
        }

        return ConstraintsBuilder.super.getConstraintsFrom(columnAnnotation);
    }
}
