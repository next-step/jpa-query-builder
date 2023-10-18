package persistence.core;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

public class EntityColumn {
    private final String name;
    private final String fieldName;
    private final Class<?> type;
    private final boolean isId;
    private final boolean isNotNull;
    private final boolean isAutoIncrement;
    private final boolean isStringValued;
    private final int stringLength;
    private final boolean isInsertable;


    public EntityColumn(final Field field) {
        field.setAccessible(true);
        this.name = initName(field);
        this.fieldName = field.getName();
        this.type = field.getType();
        this.isId = initIsId(field);
        this.isNotNull = initIsNotNull(field);
        this.isAutoIncrement = initIsAutoIncrement(field);
        this.isStringValued = this.type.isAssignableFrom(String.class);
        this.stringLength = initStringLength(field);
        this.isInsertable = initIsInsertable(field);
    }

    private String initName(final Field field) {
        final Column columnMetadata = field.getDeclaredAnnotation(Column.class);
        return Optional.ofNullable(columnMetadata)
                .filter(column -> !column.name().isEmpty())
                .map(Column::name)
                .orElse(field.getName());
    }

    private boolean initIsId(final Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean initIsNotNull(final Field field) {
        if (this.isId) {
            return true;
        }

        final Column columnMetadata = field.getDeclaredAnnotation(Column.class);
        return Optional.ofNullable(columnMetadata)
                .map(column -> !column.nullable())
                .orElse(false);
    }

    private boolean initIsAutoIncrement(final Field field) {
        final GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);
        return Optional.ofNullable(generatedValue)
                .filter(value -> value.strategy() == GenerationType.IDENTITY)
                .isPresent();
    }

    private int initStringLength(final Field field) {
        final Column columnMetadata = field.getDeclaredAnnotation(Column.class);
        return Optional.ofNullable(columnMetadata)
                .map(Column::length)
                .orElse(255);
    }

    private boolean initIsInsertable(final Field field) {
        if (this.isId) {
            return false;
        }

        final Column columnMetadata = field.getDeclaredAnnotation(Column.class);
        return Optional.ofNullable(columnMetadata)
                .map(Column::insertable)
                .orElse(true);
    }

    public String getName() {
        return this.name;
    }

    public boolean isId() {
        return this.isId;
    }

    public boolean isNotNull() {
        return this.isNotNull;
    }

    public boolean isAutoIncrement() {
        return this.isAutoIncrement;
    }

    public Class<?> getType() {
        return this.type;
    }

    public boolean isStringValued() {
        return this.isStringValued;
    }

    public int getStringLength() {
        return this.stringLength;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public boolean isInsertable() {
        return this.isInsertable;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EntityColumn that = (EntityColumn) o;
        return isId == that.isId && isNotNull == that.isNotNull && isAutoIncrement == that.isAutoIncrement && Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, isId, isNotNull, isAutoIncrement);
    }
}
