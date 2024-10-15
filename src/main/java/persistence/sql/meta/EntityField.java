package persistence.sql.meta;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public class EntityField {
    private static final Logger logger = LoggerFactory.getLogger(EntityField.class);
    private static final int DEFAULT_LENGTH = 0;

    private final List<Class<?>> quotesNeededTypes = List.of(String.class);
    private final List<Class<?>> lengthNeededTypes = List.of(String.class);
    private final Field field;

    public EntityField(Field field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityField that = (EntityField) o;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(field);
    }

    public String getValue(Object entity) {
        try {
            field.setAccessible(true);
            final String value = String.valueOf(field.get(entity));
            if (isQuotesNeeded()) {
                return String.format("'%s'", value);
            }
            return value;
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private boolean isQuotesNeeded() {
        return quotesNeededTypes.contains(getType());
    }

    public void setValue(Object entity, Object value){
        try {
            field.setAccessible(true);
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String getColumnName() {
        final Column column = field.getAnnotation(Column.class);
        if (Objects.nonNull(column) && Objects.nonNull(column.name()) && !column.name().isBlank()) {
            return column.name();
        }
        return field.getName();
    }

    public int getColumnLength() {
        final Column column = field.getAnnotation(Column.class);
        if (!lengthNeededTypes.contains(getType())) {
            return DEFAULT_LENGTH;
        }
        return column.length();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public boolean isGeneration() {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (Objects.isNull(generatedValue)) {
            return false;
        }
        return generatedValue.strategy() == GenerationType.IDENTITY;
    }

    public boolean isNotNull() {
        final Column column = field.getAnnotation(Column.class);
        if (Objects.isNull(column)) {
            return false;
        }
        return !column.nullable();
    }

    public boolean isId() {
        return field.isAnnotationPresent(Id.class);
    }

    public boolean isPersistent() {
        return !field.isAnnotationPresent(Transient.class);
    }
}
