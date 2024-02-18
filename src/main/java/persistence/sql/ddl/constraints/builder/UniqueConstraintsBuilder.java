package persistence.sql.ddl.constraints.builder;

import jakarta.persistence.Column;

public class UniqueConstraintsBuilder implements ConstraintsBuilder {

    @Override
    public String getConstraintsFrom(Column columnAnnotation) {
        if (columnAnnotation.unique()) {
            return "UNIQUE";
        }

        return ConstraintsBuilder.super.getConstraintsFrom(columnAnnotation);
    }
}
