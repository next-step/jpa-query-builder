package persistence.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class QueryBuilder {
    public static final String NOT_ENTITY_FAILED_MESSAGE = "클래스에 @Entity 애노테이션이 존재해지 않습니다.";

    private final Class<?> entityClass;

    public QueryBuilder(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(NOT_ENTITY_FAILED_MESSAGE);
        }

        this.entityClass = entityClass;
    }

    protected String build(String queryTemplate, String... templateArgs) {
        return String.format(queryTemplate, (Object[]) templateArgs);
    }

    protected String getTableName() {
        final Table table = entityClass.getAnnotation(Table.class);
        if (Objects.nonNull(table) && Objects.nonNull(table.name()) && !table.name().isBlank()) {
            return table.name();
        }
        return entityClass.getSimpleName()
                .toLowerCase();
    }

    protected List<Field> getColumns() {
        return Arrays.stream(entityClass.getDeclaredFields())
                .collect(Collectors.toList());
    }
}
