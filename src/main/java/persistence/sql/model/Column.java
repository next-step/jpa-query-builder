package persistence.sql.model;

import jakarta.persistence.Id;
import util.CaseConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Column {

    private final SqlType type;

    private final String name;

    private final List<SqlConstraint> constraints;

    public Column(Field field) {
        this.name = getName(field);
        this.type = getType(field);
        this.constraints = getConstraint(field);
    }

    private String getName(Field field) {
        String fieldName = field.getName();
        return CaseConverter.camelToSnake(fieldName);
    }

    private SqlType getType(Field field) {
        Class<?> fieldType = field.getType();
        return SqlType.of(fieldType);
    }

    private List<SqlConstraint> getConstraint(Field field) {
        List<SqlConstraint> columnConstraints = new ArrayList<>();

        Id id = field.getDeclaredAnnotation(Id.class);
        if (id != null) {
            Class<? extends Annotation> annotationType = id.annotationType();
            SqlConstraint constraint = SqlConstraint.of(annotationType);
            columnConstraints.add(constraint);
        }

        return columnConstraints;
    }

    public SqlType type() {
        return type;
    }

    public String name() {
        return name;
    }

    public List<SqlConstraint> constraints() {
        return Collections.unmodifiableList(constraints);
    }
}
