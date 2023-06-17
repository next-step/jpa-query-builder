package persistence;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EntityReflectionManager {
    private final Class<?> entity;

    public EntityReflectionManager(Class<?> entity) {
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

    public ColumnMap columnValueMap(Object object) {
        ColumnMap columnMap = new ColumnMap();

        Arrays.stream(entity.getDeclaredFields())
                .filter(this::notTransient)
                .filter(this::unique)
                .forEach(field -> columnMap.add(columnName(field), getFieldValue(field, object)));

        return columnMap;
    }

    private String getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return String.valueOf(field.get(object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean notTransient(Field field) {
        jakarta.persistence.Transient annotation = field.getAnnotation(jakarta.persistence.Transient.class);

        return annotation == null;
    }

    private boolean unique(Field field) {
        jakarta.persistence.Id annotation = field.getAnnotation(jakarta.persistence.Id.class);

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

    public String columnName(Field field) {
        jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);

        if (columnAnnotation == null) {
            return field.getName();
        }

        if (columnAnnotation.name().equals("")) {
            return field.getName();
        }

        return columnAnnotation.name();
    }

    public Field[] activeField() {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(this::notTransient)
                .toArray(Field[]::new);
    }
}
