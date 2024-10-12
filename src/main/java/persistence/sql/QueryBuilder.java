package persistence.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class QueryBuilder {
    private static final Logger logger = LoggerFactory.getLogger(QueryBuilder.class);

    public static final String NOT_ENTITY_FAILED_MESSAGE = "클래스에 @Entity 애노테이션이 존재해지 않습니다.";

    private final Object entity;

    public QueryBuilder(Object entity) {
        if (!entity.getClass().isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(NOT_ENTITY_FAILED_MESSAGE);
        }

        this.entity = entity;
    }

    public abstract String build();

    protected String build(String queryTemplate, String... templateArgs) {
        return String.format(queryTemplate, getArgs(templateArgs));
    }

    protected List<Field> getColumns() {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .collect(Collectors.toList());
    }

    protected Object getValue(Field field) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private Object[] getArgs(String[] templateArgs) {
        Object[] args = new Object[templateArgs.length + 1];
        args[0] = getTableName();
        System.arraycopy(templateArgs, 0, args, 1, templateArgs.length);
        return args;
    }

    private String getTableName() {
        final Table table = entity.getClass().getAnnotation(Table.class);
        if (Objects.nonNull(table) && Objects.nonNull(table.name()) && !table.name().isBlank()) {
            return table.name();
        }
        return entity.getClass()
                .getSimpleName()
                .toLowerCase();
    }
}
