package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.sql.ddl.collection.IdGeneratedValueStrategyMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
public class DdlQueryBuilder {
    private static final String CREATE_TABLE = "create table %s (%s);";
    private static final String BLANK = " ";
    private static final String COMMA = ",";

    private final Map<String, String> idColumns = new LinkedHashMap<>();
    private final Map<String, String> columns = new LinkedHashMap<>();
    private final JavaToSqlColumnParser javaToSqlColumnParser;
    private final IdGeneratedValueStrategyMap idGeneratedValueStrategyMap;
    private Class<?> entity;

    public DdlQueryBuilder(JavaToSqlColumnParser javaToSqlColumnParser) {
        this.javaToSqlColumnParser = javaToSqlColumnParser;
        this.idGeneratedValueStrategyMap = new IdGeneratedValueStrategyMap();
    }

    public String build(Class<?> entity) {
        if (entity == null) {
            throw new IllegalStateException("Entity is not set");
        }
        this.entity = entity;
        setFields();
        return buildSql();
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
            idColumns.put(getColumnName(field), getColumnTypeAndConstraint(field));
            return;
        }
        columns.put(getColumnName(field), getColumnTypeAndConstraint(field));
    }

    private String getColumnName(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName().toLowerCase();
        }
        final Column column = field.getAnnotation(Column.class);
        if (column.name().isBlank()) {
            return field.getName().toLowerCase();
        }
        return column.name();
    }

    private String getColumnTypeAndConstraint(Field field) {
        StringBuilder sb = new StringBuilder();
        if (field.isAnnotationPresent(Column.class)) {
            final Column column = field.getAnnotation(Column.class);
            sb.append(javaToSqlColumnParser.parse(field.getType(), column));
        }
        if (!field.isAnnotationPresent(Column.class)) {
            sb.append(javaToSqlColumnParser.parse(field.getType()));
        }

        if (field.isAnnotationPresent(GeneratedValue.class)) {
            final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            sb.append(BLANK)
                    .append(idGeneratedValueStrategyMap.get(generatedValue.strategy()));
        }
        return sb.toString();
    }

    private String buildSql() {
        String content = addColumns(idColumns) +
                addColumns(columns) +
                addConstraint();
        return String.format(CREATE_TABLE, getTableName(), content);
    }

    private String getTableName() {
        if (entity.isAnnotationPresent(Table.class)) {
            final Table table = entity.getAnnotation(Table.class);
            if (table.name().isBlank()) {
                return entity.getSimpleName().toLowerCase();
            }
            return table.name();
        }
        return entity.getSimpleName().toLowerCase();
    }

    private String addConstraint() {
        StringBuilder sb = new StringBuilder();
        if (!idColumns.isEmpty()) {
            sb.append(" constraint pk_")
                    .append(entity.getSimpleName().toLowerCase())
                    .append(" primary key (");
            final String join = String.join(", ", idColumns.keySet());
            sb.append(join);
            sb.append(")");
        }
        return sb.toString();
    }

    private String addColumns(Map<String, String> columns) {
        StringBuilder sb = new StringBuilder();
        columns.forEach((key, value) -> {
            sb.append(key);
            sb.append(BLANK);
            sb.append(value);
            sb.append(COMMA);
        });
        return sb.toString();
    }

    public Map<String, String> getIdColumns() {
        return idColumns;
    }

    public Map<String, String> getColumns() {
        return columns;
    }
}
