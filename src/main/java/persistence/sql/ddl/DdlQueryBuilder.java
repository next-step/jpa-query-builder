package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DdlQueryBuilder {
    public static final String CREATE_FAILED_MESSAGE = "클래스에 @Entity 애노테이션이 존재해지 않습니다.";
    private static final String CREATE_QUERY_TEMPLATE = "CREATE TABLE %s ( %s )";

    private static final String NOT_NULL_COLUMN_DEFINITION = "NOT NULL";
    private static final String GENERATION_COLUMN_DEFINITION = "AUTO_INCREMENT";
    private static final String PRIMARY_KEY_COLUMN_DEFINITION = "PRIMARY KEY";

    private static final Map<Class<?>, String> FIELD_TYPE_TO_DB_TYPE_MAPPING;

    private final Class<?> entityClass;

    static {
        FIELD_TYPE_TO_DB_TYPE_MAPPING = Map.of(
                String.class, "VARCHAR(255)",
                Integer.class, "INTEGER",
                Long.class, "BIGINT"
        );
    }

    public DdlQueryBuilder(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(CREATE_FAILED_MESSAGE);
        }

        this.entityClass = entityClass;
    }

    public String build() {
        return String.format(CREATE_QUERY_TEMPLATE, getTableName(), getColumns());
    }

    private String getTableName() {
        final Table table = entityClass.getAnnotation(Table.class);
        if (Objects.nonNull(table) && Objects.nonNull(table.name()) && !table.name().isBlank()) {
            return table.name();
        }
        return entityClass.getSimpleName()
                .toLowerCase();
    }

    private String getColumns() {
        final List<String> columnDefinitions = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::getColumnDefinition)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private String getColumnDefinition(Field field) {
        String columDefinition = getColumnName(field) + " " + getDbType(field);

        if (!isNullable(field)) {
            columDefinition += " " + NOT_NULL_COLUMN_DEFINITION;
        }

        if (isGeneration(field)) {
            columDefinition += " " + GENERATION_COLUMN_DEFINITION;
        }

        if (isPrimaryKey(field)) {
            columDefinition += " " + PRIMARY_KEY_COLUMN_DEFINITION;
        }

        return columDefinition;
    }

    private String getColumnName(Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (Objects.nonNull(column) && Objects.nonNull(column.name()) && !column.name().isBlank()) {
            return column.name();
        }
        return field.getName();
    }

    private String getDbType(Field field) {
        return FIELD_TYPE_TO_DB_TYPE_MAPPING.get(field.getType());
    }

    private boolean isNullable(Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (Objects.isNull(column)) {
            return true;
        }
        return column.nullable();
    }

    private boolean isGeneration(Field field) {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (Objects.isNull(generatedValue)) {
            return false;
        }
        return generatedValue.strategy() == GenerationType.IDENTITY;
    }

    private boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }
}
