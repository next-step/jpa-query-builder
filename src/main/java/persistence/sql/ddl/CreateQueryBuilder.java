package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateQueryBuilder {
    public static final String CREATE_FAILED_MESSAGE = "클래스에 @Entity 애노테이션이 존재해지 않습니다.";
    private static final String CREATE_QUERY_TEMPLATE = "CREATE TABLE %s ( %s )";
    private static final String PRIMARY_KEY_COLUMN_DEFINITION = "NOT NULL PRIMARY KEY";
    private static final Map<Class<?>, String> FIELD_TYPE_TO_DB_TYPE_MAPPING;

    private final Class<?> entityClass;

    static {
        FIELD_TYPE_TO_DB_TYPE_MAPPING = Map.of(
                String.class, "VARCHAR(255)",
                Integer.class, "INTEGER",
                Long.class, "BIGINT"
        );
    }

    public CreateQueryBuilder(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(CREATE_FAILED_MESSAGE);
        }

        this.entityClass = entityClass;
    }

    public String build() {
        return String.format(CREATE_QUERY_TEMPLATE, getTableName(), getColumns());
    }

    private String getTableName() {
        return entityClass.getSimpleName()
                .toLowerCase();
    }

    private String getColumns() {
        final List<String> columnDefinitions = Arrays.stream(entityClass.getDeclaredFields())
                .map(this::getColumnDefinition)
                .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private String getColumnDefinition(Field field) {
        String columDefinition = field.getName() + " " + getDbType(field);
        if (field.isAnnotationPresent(Id.class)) {
            columDefinition += " " + PRIMARY_KEY_COLUMN_DEFINITION;
        }
        return columDefinition;
    }

    private String getDbType(Field field) {
        return FIELD_TYPE_TO_DB_TYPE_MAPPING.get(field.getType());
    }
}
