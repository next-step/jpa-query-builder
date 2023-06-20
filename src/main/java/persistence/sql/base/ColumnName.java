package persistence.sql.base;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class ColumnName {
    private final Field field;
    private final Column column;

    public ColumnName(Field field) {
        this(field, field.getAnnotation(Column.class));
    }

    public ColumnName(Field field, Column column) {
        this.field = field;
        this.column = column;
    }

    public static ColumnName id(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();

        return Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .map(ColumnName::new)
                .orElseThrow(() -> new IllegalArgumentException("@Id 어노테이션이 선언된 필드가 존재하지 않습니다."));
    }

    public String name() {
        if (column == null || column.name().isBlank()) {
            return field.getName();
        }

        return column.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnName that = (ColumnName) o;
        return Objects.equals(field, that.field) && Objects.equals(column, that.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, column);
    }
}
