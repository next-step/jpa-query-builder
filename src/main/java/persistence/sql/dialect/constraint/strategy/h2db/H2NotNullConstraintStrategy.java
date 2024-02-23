package persistence.sql.dialect.constraint.strategy.h2db;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import persistence.sql.dialect.constraint.strategy.ColumnConstraintStrategy;

import java.lang.reflect.Field;
import java.util.Objects;

public class H2NotNullConstraintStrategy implements ColumnConstraintStrategy {

    @Override
    public String generateConstraints(Field field) {
        Id primaryKeyAnnotation = field.getDeclaredAnnotation(Id.class);
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);

        if (Objects.nonNull(primaryKeyAnnotation) || (Objects.nonNull(columnAnnotation) && !columnAnnotation.nullable())) {
            return "NOT NULL";
        }

        return "";
    }
}
