package persistence.sql;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class QueryBuilder {
    protected static final String BLANK = " ";
    protected static final String COMMA = ",";
    protected static final String SINGLE_QUOTE = "'";

    protected Class<?> entity;
    protected final Map<String, Field> idColumns = new LinkedHashMap<>();
    protected final Map<String, Field> columns = new LinkedHashMap<>();

    protected QueryBuilder(Class<?> entity) {
        if (entity == null) {
            throw new IllegalStateException("Entity is not set");
        }
        this.entity = entity;
        setFields();
    }

    protected String getTableName() {
        if (entity.isAnnotationPresent(Table.class)) {
            final Table table = entity.getAnnotation(Table.class);
            if (table.name().isBlank()) {
                return entity.getSimpleName().toLowerCase();
            }
            return table.name();
        }
        return entity.getSimpleName().toLowerCase();
    }

    private void setFields() {
        final Field[] declaredFields = entity.getDeclaredFields();
        Arrays.stream(declaredFields)
                .forEach(this::addColumns);
    }

    private void addColumns(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return;
        }
        if (field.isAnnotationPresent(Id.class)) {
            idColumns.put(getColumnName(field), field);
            return;
        }
        columns.put(getColumnName(field), field);
    }

    protected String getColumnName(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName().toLowerCase();
        }
        final Column column = field.getAnnotation(Column.class);
        if (column.name().isBlank()) {
            return field.getName().toLowerCase();
        }
        return column.name();
    }

    public Map<String, Field> getIdColumns() {
        return idColumns;
    }

    public Map<String, Field> getColumns() {
        return columns;
    }

    protected String joinWithComma(Collection<String> fields) {
        return String.join(COMMA, fields);
    }

    protected Predicate<Field> isNotTransientField() {
        return field -> !field.isAnnotationPresent(Transient.class);
    }

    protected Predicate<Field> isNotGeneratedIdField() {
        return field -> {
            final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue == null) {
                return true;
            }

            return generatedValue.strategy() != GenerationType.IDENTITY;
        };
    }
}
