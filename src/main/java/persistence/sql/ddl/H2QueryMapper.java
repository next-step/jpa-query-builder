package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class H2QueryMapper {

    public String createQuery(Class<?> clazz) {
        String tableQuery = tableQuery(clazz);
        String columnsQuery = columnsQuery(clazz);

        return String.format("%s %s", tableQuery, columnsQuery);
    }

    private String tableQuery(Class<?> clazz) {
        return String.format("create table %s", getTableName(clazz));
    }

    private String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);

        if (table != null && !table.name().isBlank()) {
            return String.format(table.name());
        }

        return String.format(clazz.getSimpleName());
    }

    private String columnsQuery(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        String idColumnQuery = getIdColumnQuery(declaredFields);
        String columnsQuery = getColumnsQuery(declaredFields);

        return String.format("(%s, %s)", idColumnQuery, columnsQuery);
    }

    private String getIdColumnQuery(Field[] declaredFields) {
        Field idField = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("@Id 어노테이션이 선언된 필드가 존재하지 않습니다."));

        StringBuilder queryBuilder = new StringBuilder("%s primary key");
        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);

        if (generatedValue != null && generatedValue.strategy().equals(GenerationType.IDENTITY)) {
            queryBuilder.append(" auto_increment");
        }

        return String.format(queryBuilder.toString(), getColumnQuery(idField));
    }

    private String getColumnsQuery(Field[] declaredFields) {
        List<Field> fields = Arrays.stream(declaredFields)
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .filter(filed -> !filed.isAnnotationPresent(Transient.class))
                .toList();

        return fields.stream()
                .map(this::getColumnQuery)
                .collect(Collectors.joining(", "));
    }

    private String getColumnQuery(Field field) {
        Column column = field.getAnnotation(Column.class);

        if (column == null) {
            return getNoneColumnAnnotationQuery(field);
        }

        String columnName = getColumnName(column, field);
        String columnType = getColumnType(field.getType(), column);
        return String.format("%s %s", columnName, columnType);
    }

    private String getNoneColumnAnnotationQuery(Field field) {
        String columnName = field.getName();
        Class<?> type = field.getType();
        String columnType = getColumnType(type);

        if (type.equals(String.class)) {
            columnType = columnType + "(255)";
        }

        return String.format("%s %s", columnName, columnType);
    }

    private String getColumnName(Column column, Field field) {
        String name = column.name();

        if (name.isBlank()) {
            return field.getName();
        }

        return name;
    }

    private String getColumnType(Class<?> type, Column column) {
        String columnTypeWithLength = String.format("%s(%d)", getColumnType(type), column.length());
        StringBuilder builder = new StringBuilder(columnTypeWithLength);

        if (!column.nullable()) {
            builder.append(" not null");
        }

        return builder.toString();
    }

    private String getColumnType(Class<?> type) {
        if (type.equals(Long.class)) {
            return "bigint";
        }

        if (type.equals(String.class)) {
            return "varchar";
        }

        if (type.equals(Integer.class)) {
            return "integer";
        }

        throw new IllegalArgumentException("존재하지 않는 컬럼 타입 입니다.");
    }
}
