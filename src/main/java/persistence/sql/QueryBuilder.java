package persistence.sql;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class QueryBuilder {
    protected static final String BLANK = " ";
    protected static final String COMMA = ",";
    protected static final String SINGLE_QUOTE = "'";
    protected static final String NULL = "null";

    protected Class<?> entity;
    protected final Columns idColumns = new Columns();
    protected final Columns columns = new Columns();

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

    public Set<String> getIdColumns() {
        return idColumns.getColumnNames();
    }

    public Set<String> getColumns() {
        return columns.getColumnNames();
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

    protected List<Field> getAllColumnFields() {
        List<Field> results = new ArrayList<>();
        results.addAll(idColumns.getColumnFields());
        results.addAll(columns.getColumnFields());
        return results;
    }

    protected Map<Class<?>, Field> getAllColumMap() {
        List<Field> results = new ArrayList<>();
        results.addAll(idColumns.getColumnFields());
        results.addAll(columns.getColumnFields());
        return results.stream().collect(Collectors.toMap(Field::getType, Function.identity()));
    }

    protected String getIdColumnName() {
        return joinWithComma(idColumns.getColumnFields().stream().map(this::getColumnName).collect(Collectors.toList()));
    }
}
