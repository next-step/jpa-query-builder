package persistence.sql.dialect.constraint.strategy.constraint;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import persistence.sql.dialect.constraint.strategy.ColumnConstraintStrategy;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;

public class NotNullConstraint implements ColumnConstraintStrategy {

    public static final String EMPTY_NOT_NULL_ANNOTATION = "";

    @Override
    public String generateConstraints(List<Annotation> annotations) {
        Id primaryKeyAnnotation = (Id) annotations.stream()
                .filter(annotation -> annotation.annotationType().equals(Id.class))
                .findFirst()
                .orElse(null);

        Column columnAnnotation = (Column) annotations.stream()
                .filter(annotation -> annotation.annotationType().equals(Column.class))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(primaryKeyAnnotation) || (Objects.nonNull(columnAnnotation) && !columnAnnotation.nullable())) {
            return "NOT NULL";
        }

        return EMPTY_NOT_NULL_ANNOTATION;
    }
}
