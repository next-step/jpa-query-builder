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
        TableInfo tableInfo = new TableInfo(entity);
        String columnDefinitions = this.generateColumnDefinitions(entity);

        return "CREATE TABLE %s (%s);".formatted(tableInfo.getTableName(), columnDefinitions);
    }

    public String drop(Class<?> entity) {
        TableInfo tableInfo = new TableInfo(entity);
        return "DROP TABLE %s;".formatted(tableInfo.getTableName());
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
}
