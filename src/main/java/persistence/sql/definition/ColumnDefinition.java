package persistence.sql.definition;

import jakarta.persistence.Column;
import org.jetbrains.annotations.Nullable;
import persistence.sql.SqlType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

public class ColumnDefinition {
    private static final int DEFAULT_LENGTH = 255;

    private final String name;
    private final SqlType sqlType;
    private final String declaredName;
    private final boolean nullable;
    private final int length;

    public ColumnDefinition(Field field) {
        this.declaredName = field.getName();
        this.name = determineColumnName(field);
        this.sqlType = determineColumnType(field);
        this.nullable = determineColumnNullable(field);
        this.length = determineColumnLength(field);
    }

    private static String determineColumnName(Field field) {
        final String columnName = field.getName();

        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }

        return columnName;
    }

    private static SqlType determineColumnType(Field field) {
        final String entityFieldType = field.getType().getSimpleName();
        return SqlType.from(entityFieldType);
    }

    private static int determineColumnLength(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            return column.length();
        }
        return DEFAULT_LENGTH;
    }

    private static boolean determineColumnNullable(Field field) {
        final boolean hasColumnAnnotation = field.isAnnotationPresent(Column.class);
        if (!hasColumnAnnotation) {
            return true;
        }
        return field.getAnnotation(Column.class).nullable();
    }

    public String name() {
        return name;
    }

    public SqlType sqlType() {
        return sqlType;
    }

    public boolean shouldNotNull() {
        return !nullable;
    }

    public int length() {
        return length;
    }

    public boolean nullable() {
        return nullable;
    }

    public String declaredName() {
        return declaredName;
    }

    @Nullable
    public Object valueAsString(Object entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        return Arrays.stream(declaredFields).sequential()
                .filter(field -> field.getName().equals(declaredName()))
                .map(field -> {
                    boolean wasAccessible = field.canAccess(entity);
                    try {
                        if (!wasAccessible) {
                            field.setAccessible(true);
                        }

                        return Optional.ofNullable(field.get(entity));
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Cannot access field value", e);
                    } finally {
                        if (!wasAccessible) {
                            field.setAccessible(false);
                        }
                    }

                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Field not found"))
                .orElse(null);
    }
}
