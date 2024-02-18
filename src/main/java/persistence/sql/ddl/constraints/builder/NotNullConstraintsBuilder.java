package persistence.sql.ddl.constraints.builder;

import jakarta.persistence.Column;

public class NotNullConstraintsBuilder implements ConstraintsBuilder {

    @Override
    public String getConstraintsFrom(Column columnAnnotation) {
        if (!columnAnnotation.nullable()) {
            return "NOT NULL";
        }

        return ConstraintsBuilder.super.getConstraintsFrom(columnAnnotation);
    }
}
