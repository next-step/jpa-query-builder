package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {
    private final ColumnDefinitionMapper columnDefinitionMapper = new ColumnDefinitionMapper();

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

        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> "%s %s".formatted(this.getColumnNameFromAnnotation(field), this.getColumnTypeFromAnnotation(field)))
                .collect(Collectors.joining(", "));
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
        List<String> columnNameWithDefinition = new ArrayList<>();
        columnNameWithDefinition.add(ColumnType.getSqlType(field.getType()));
        columnNameWithDefinition.addAll(this.columnDefinitionMapper.mapAnnotationToSQLDefinition(field));

        return columnNameWithDefinition
                .stream()
                .filter(definition -> !definition.isEmpty())
                .collect(Collectors.joining(" "));
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
