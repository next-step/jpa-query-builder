package persistence.sql.ddl;

import jakarta.persistence.Transient;
import persistence.sql.ddl.mapper.ConstraintMapper;
import persistence.sql.ddl.mapper.TypeMapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Column {

    private static final String SPACE = " ";

    private final String name;

    private final String type;

    private final Constraints constraints;

    private final boolean isTransient;

    public Column(Field field, TypeMapper typeMapper, ConstraintMapper constraintMapper) {
        this.name = generateColumnName(field);
        this.type = generateColumnType(field, typeMapper);
        this.constraints = generateColumnConstraints(field, constraintMapper);
        this.isTransient = generateIsTransient(field);
    }

    public String generateColumnName(Field field) {
        if (!field.isAnnotationPresent(jakarta.persistence.Column.class)) {
            return field.getName().toUpperCase();
        }

        jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);
        if (column.name().isEmpty()) {
            return field.getName().toUpperCase();
        }
        return column.name().toUpperCase();
    }

    public String generateColumnType(Field field, TypeMapper typeMapper) {
        return typeMapper.getType(field);
    }

    public Constraints generateColumnConstraints(Field field, ConstraintMapper constraintMapper) {
        return constraintMapper.getConstraints(field);
    }

    public boolean generateIsTransient(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    public String generateColumn() {
        return Stream.of(name, type, constraints.getConstraintsQuery())
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(SPACE));
    }

    public boolean isNotTransient() {
        return !isTransient;
    }
}
