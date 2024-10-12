package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Map;
import java.util.Objects;

public class DropQueryBuilder {
    public static final String CREATE_FAILED_MESSAGE = "클래스에 @Entity 애노테이션이 존재해지 않습니다.";
    private static final String DROP_QUERY_TEMPLATE = "DROP TABLE IF EXISTS %s";

    private static final Map<Class<?>, String> FIELD_TYPE_TO_DB_TYPE_MAPPING;

    private final Class<?> entityClass;

    static {
        FIELD_TYPE_TO_DB_TYPE_MAPPING = Map.of(
                String.class, "VARCHAR(255)",
                Integer.class, "INTEGER",
                Long.class, "BIGINT"
        );
    }

    public DropQueryBuilder(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(CREATE_FAILED_MESSAGE);
        }

        this.entityClass = entityClass;
    }

    public String getDropQuery() {
        return String.format(DROP_QUERY_TEMPLATE, getTableName());
    }

    private String getTableName() {
        final Table table = entityClass.getAnnotation(Table.class);
        if (Objects.nonNull(table) && Objects.nonNull(table.name()) && !table.name().isBlank()) {
            return table.name();
        }
        return entityClass.getSimpleName()
                .toLowerCase();
    }
}
