package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryBuilder {
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
            .map(field -> {
                String columnName = new ColumnInfo(field).getColumnName();
                String columnDataType = ColumnDataType.getSqlType(field.getType());
                String columnType = this.getColumnTypeFromAnnotation(field);
                return "%s %s %s".formatted(columnName, columnDataType, columnType).trim();
            }).collect(Collectors.joining(", "));
    }



    private String getColumnTypeFromAnnotation(Field field) {
        ColumnDefinitionMapper columnDefinitionMapper = new ColumnDefinitionMapper(field);
        List<String> columnNameWithDefinition = new ArrayList<>(columnDefinitionMapper.mapAnnotationToSQLDefinition());

        return columnNameWithDefinition
                .stream()
                .filter(definition -> !definition.isEmpty())
                .collect(Collectors.joining(" "));
    }
}
