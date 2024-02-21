package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DDLGenerator {

    public static final String DOT = "\\.";

    public String generateCreate(Class<?> entity) {
        String tableName = getTableName(entity);
        String sql = "CREATE TABLE " + tableName + " (";

        sql += Arrays.stream(entity.getDeclaredFields())
                .map(DDLGenerator::defineColumn)
                .collect(Collectors.joining(", "));

        String id = Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(Field::getName)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Persistent entity '%s' should have primary key", entity.getName())));

        sql += String.format(", PRIMARY KEY(%s)", id);

        sql += ");";
        return sql;
    }

    private static String defineColumn(Field field) {
        Column column = field.getAnnotation(Column.class);
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);

        String columnName = column != null ? column.name() : field.getName();
        String columnType = ColumnType.findColumnType(field.getType());
        String columnLength = column != null ? String.format("(%s)", column.length()) : "";
        String generatedValueStrategy = generatedValue != null ? " " + generatedValue.strategy().name() + " " : "";
        String nullable = column != null && !column.nullable() ? " not null" : " null";

        return columnName + " " + columnType + columnLength + generatedValueStrategy + nullable;
    }


    private static String getTableName(Class<?> entity) {
        String name = entity.getName();

        String[] splitByDotName = name.split(DOT);

        return splitByDotName[splitByDotName.length - 1].toLowerCase();
    }
}
