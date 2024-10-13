package persistence.sql;

import jakarta.persistence.Entity;
import persistence.sql.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Table {
    public static final String NOT_ENTITY_FAILED_MESSAGE = "클래스에 @Entity 애노테이션이 없습니다.";
    public static final String NOT_ID_FAILED_MESSAGE = "필드에 @Id 애노테이션이 없습니다.";

    private final Class<?> entityClass;

    public Table(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(NOT_ENTITY_FAILED_MESSAGE);
        }

        this.entityClass = entityClass;
    }

    public String getQuery(String queryTemplate, String... templateArgs) {
        return String.format(queryTemplate, (Object[]) templateArgs);
    }

    public String getTableName() {
        final jakarta.persistence.Table table = entityClass.getAnnotation(jakarta.persistence.Table.class);
        if (Objects.nonNull(table) && Objects.nonNull(table.name()) && !table.name().isBlank()) {
            return table.name();
        }
        return entityClass.getSimpleName()
                .toLowerCase();
    }

    public List<Field> getFields() {
        return Arrays.stream(entityClass.getDeclaredFields())
                .collect(Collectors.toList());
    }

    public String getWhereClause(Object id) {
        final Field field = getIdField();
        return FieldUtils.getColumnName(field) + " = " + id;
    }

    public Field getIdField() {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(FieldUtils::isId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(NOT_ID_FAILED_MESSAGE));
    }
}
