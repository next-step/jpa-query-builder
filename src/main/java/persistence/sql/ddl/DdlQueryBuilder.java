package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static persistence.sql.ddl.DbDialect.*;

public class DdlQueryBuilder {
    private static final String CREATE_TABLE = "create table %s (%s);";
    private static final String BLANK = " ";
    private static final String COMMA = ",";
    private final Map<Class<?>, DbDialect> JAVA_TO_SQL;
    private final Map<String, Class<?>> idColumns = new LinkedHashMap<>();
    private final Map<String, Class<?>> columns = new LinkedHashMap<>();
    private Class<?> entity;

    public DdlQueryBuilder() {
        JAVA_TO_SQL = Map.of(
                Long.class, LONG,
                Long.TYPE, LONG,
                String.class, STRING,
                Integer.class, INTEGER,
                Integer.TYPE, INTEGER
        );
    }

    public DdlQueryBuilder create(Class<?> entity) {
        this.entity = entity;
        setFields(entity);
        return this;
    }

    private void setFields(Class<?> entity) {
        final Field[] declaredFields = entity.getDeclaredFields();
        Arrays.stream(declaredFields)
                .forEach(this::addColumns);
    }

    private void addColumns(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            idColumns.put(getColumnName(field), field.getType());
            return;
        }
        columns.put(getColumnName(field), field.getType());
    }

    private String getColumnName(Field field) {
        return field.getName().toLowerCase();
    }

    private String getColumnType(Class<?> javaType) {
        return JAVA_TO_SQL.get(javaType).getSqlType();
    }

    public String build() {
        if (entity == null) {
            throw new IllegalStateException("Entity is not set");
        }
        String content = addColumns(idColumns) +
                addColumns(columns) +
                addConstraint();
        return String.format(CREATE_TABLE, entity.getSimpleName().toLowerCase(), content);
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

    private String addColumns(Map<String, Class<?>> columns) {
        StringBuilder sb = new StringBuilder();
        columns.forEach((key, value) -> {
            sb.append(key);
            sb.append(BLANK);
            sb.append(getColumnType(value));
            sb.append(COMMA);
        });
        return sb.toString();
    }

    public Map<String, Class<?>> getIdColumns() {
        return idColumns;
    }

    public Map<String, Class<?>> getColumns() {
        return columns;
    }
}
