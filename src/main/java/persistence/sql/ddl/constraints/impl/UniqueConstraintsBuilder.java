package persistence.sql.ddl.constraints.impl;

import jakarta.persistence.Column;
import persistence.sql.ddl.constraints.ConstraintsBuilder;

public class UniqueConstraintsBuilder implements ConstraintsBuilder {

    @Override
    public String getConstraintsFrom(Column columnAnnotation) {
        if (columnAnnotation.unique()) {
            return "UNIQUE";
        }

        return ConstraintsBuilder.super.getConstraintsFrom(columnAnnotation);
    }
}
