package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class DdlCreateQueryBuilder {

    private static final String NO_ENTITY_ANNOTATION = "@Entity 어노테이션이 존재하지 않습니다";
    private static final Map<Class<?>, String> FIELD_TYPE_TO_DB_TYPE = Map.of(
        String.class, "VARCHAR(255)",
        Integer.class, "INTEGER",
        Long.class, "BIGINT"
    );

    private final Class<?> entityClass;

    public DdlCreateQueryBuilder(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(NO_ENTITY_ANNOTATION);
        }

        this.entityClass = entityClass;
    }

    public String build() {
        return "CREATE TABLE" +
            " " +
            getTableName() +
            " (" +
            getColumns() +
            ")";
    }

    private String getTableName() {
        return entityClass.getSimpleName()
            .toLowerCase();
    }

    private String getColumns() {
        final List<String> columnDefinitions = Arrays.stream(entityClass.getDeclaredFields())
            .map(this::getColumnDdl)
            .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private String getColumnDdl(Field field) {
        String columnDdl = field.getName() +
            " " +
            FIELD_TYPE_TO_DB_TYPE.get(field.getType());
        if (field.isAnnotationPresent(Id.class)) {
            columnDdl += " NOT NULL PRIMARY KEY";
        }
        return columnDdl;
    }

}

