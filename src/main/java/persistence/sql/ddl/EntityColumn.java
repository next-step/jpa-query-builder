package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

public class EntityColumn {
    private final String name;
    private final Class<?> type;
    private final boolean isId;
    private final boolean isNotNull;
    private final boolean isAutoIncrement;
    private final boolean isTransient;


    public EntityColumn(final Field field) {
        field.setAccessible(true);
        this.name = initName(field);
        this.type = field.getType();
        this.isId = initIsId(field);
        this.isNotNull = this.isId || initIsNotNull(field);
        this.isAutoIncrement = initIsAutoIncrement(field);
        this.isTransient = initIsTransient(field);
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


    private boolean initIsTransient(final Field field) {
        return field.isAnnotationPresent(Transient.class);
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

    public boolean isTransient() {
        return this.isTransient;
    }

    public Class<?> getType() {
        return this.type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EntityColumn that = (EntityColumn) o;
        return isId == that.isId && isNotNull == that.isNotNull && isAutoIncrement == that.isAutoIncrement && isTransient == that.isTransient && Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, isId, isNotNull, isAutoIncrement, isTransient);
    }
}
