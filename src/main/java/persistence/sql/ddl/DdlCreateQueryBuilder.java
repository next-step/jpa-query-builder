package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null && table.name() != null && !table.name().isEmpty()) {
            return table.name();
        }
        return entityClass.getSimpleName()
            .toLowerCase();
    }

    private String getColumns() {
        final List<String> columnDefinitions = Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .map(this::getColumnDdl)
            .collect(Collectors.toList());

        return String.join(", ", columnDefinitions);
    }

    private String getColumnDdl(Field field) {
        String columnDdl = getColumnName(field) +
            " " +
            FIELD_TYPE_TO_DB_TYPE.get(field.getType());

        if (isNotNull(field)) {
            columnDdl += " NOT NULL";
        }

        if (isGeneratedValue(field)) {
            columnDdl += " AUTO_INCREMENT";
        }

        if (isId(field)) {
            columnDdl += " PRIMARY KEY";
        }
        return columnDdl;
    }

    private String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null && column.name() != null && !column.name().isEmpty()) {
            return column.name();
        }

        return field.getName();
    }

    private Boolean isNotNull(Field field) {
        return isNullable(field);
    }

    private Boolean isNullable(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column == null) {
            return true;
        }

        return column.nullable();
    }

    private Boolean isGeneratedValue(Field field) {
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (generatedValue == null) {
            return false;
        }

        return generatedValue.strategy() == GenerationType.IDENTITY;
    }

    private Boolean isId(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

}

