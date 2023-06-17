package persistence;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EntityScanner {
    private final Class<?> entity;

    public EntityScanner(Class<?> entity) {
        this.entity = entity;
    }

    public Table table() {
        return new Table(entity.getSimpleName());
    }

    public Columns columns() {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(this::notTransient)
                .map(it -> ColumnNode.of(
                        columnName(it),
                        it.getType(),
                        columnSize(it),
                        it.isAnnotationPresent(Id.class),
                        columnNullable(it)
                ))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Columns::new));
    }

    private boolean notTransient(Field field) {
        jakarta.persistence.Transient annotation = field.getAnnotation(jakarta.persistence.Transient.class);

        return annotation == null;
    }

    private boolean columnNullable(Field field) {
        jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);

        if (columnAnnotation == null) {
            return false;
        }

        return columnAnnotation.nullable();
    }

    private int columnSize(Field field) {
        jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);

        if (columnAnnotation == null) {
            return -1;
        }

        return columnAnnotation.length();
    }

    private String columnName(Field field) {
        jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);

        if (columnAnnotation == null) {
            return field.getName();
        }

        if (columnAnnotation.name().equals("")) {
            return field.getName();
        }

        return columnAnnotation.name();
    }
}
