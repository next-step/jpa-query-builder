package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {
    private final FieldAnnotationMapper fieldAnnotationMapper = new FieldAnnotationMapper();

    public String create(Class<?> entity) {
        String createTableQuery = this.getCreateTableQuery(entity);
        String columnDefinitions = this.generateColumnDefinitions(entity);

        return "%s (%s);".formatted(createTableQuery, columnDefinitions);
    }

    public String drop(Class<?> entity) {
        return "DROP TABLE %s;".formatted(this.getTableName(entity));
    }

    private String getCreateTableQuery(Class<?> entity) {
        return "CREATE TABLE %s".formatted(this.getTableName(entity));
    }

    private String generateColumnDefinitions(Class<?> entity) {
        Field[] fields = entity.getDeclaredFields();
        List<String> columns = Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> "%s %s".formatted(this.getColumnNameFromAnnotation(field), this.getColumnTypeFromAnnotation(field)))
                .toList();
        return String.join(", ", columns);
    }

    private String getColumnNameFromAnnotation(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }
        String columnName = field.getAnnotation(Column.class).name();
        if (columnName.isEmpty()) {
            return field.getName();
        }
        return columnName;
    }

    private String getColumnTypeFromAnnotation(Field field) {
        return ColumnType.getSqlType(field.getType()) + this.fieldAnnotationMapper.mapFieldAnnotationToSQLType(field);
    }

    private String getTableName(Class<?> entity) {
        this.throwIfNotEntity(entity);
        Table table = entity.getAnnotation(Table.class);
        if (table == null) {
            return entity.getSimpleName();
        }
        if (table.name().isBlank()) {
            return entity.getSimpleName();
        }
        return table.name();
    }

    private void throwIfNotEntity(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class %s is not an entity".formatted(entity.getSimpleName()));
        }
    }
}
