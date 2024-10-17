package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                List<String> columnInfo = new ArrayList<>();
                ColumnDefinitionMapper columnDefinitionMapper = new ColumnDefinitionMapper(field);
                String columnName = columnDefinitionMapper.getColumnName();
                String columnDataType = ColumnDataType.getSqlType(field.getType());

                columnInfo.add(columnName);
                columnInfo.add(columnDataType);
                columnInfo.addAll(columnDefinitionMapper.mapAnnotationToSQLDefinition());

                columnInfo.removeAll(Arrays.asList("", null));
                return String.join(" ", columnInfo);
            }).collect(Collectors.joining(", "));
    }
}
