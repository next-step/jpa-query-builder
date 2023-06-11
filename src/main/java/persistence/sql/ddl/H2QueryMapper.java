package persistence.sql.ddl;

import jakarta.persistence.Id;

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

        return String.format("%s primary key", getColumnQuery(idField));
    }

    private String getColumnsQuery(Field[] declaredFields) {
        List<Field> fields = Arrays.stream(declaredFields)
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();

        return fields.stream()
                .map(this::getColumnQuery)
                .collect(Collectors.joining(", "));
    }

    private String getColumnQuery(Field field) {
        String columnName = field.getName();
        String columnType = columnType(field.getType());
        return String.format("%s %s", columnName, columnType);
    }

    private static String columnType(Class<?> type) {
        if (type.equals(Long.class)) {
            return "bigint";
        }

        if (type.equals(String.class)) {
            return "varchar(255)";
        }

        if (type.equals(Integer.class)) {
            return "integer";
        }

        throw new IllegalArgumentException("존재하지 않는 컬럼 타입 입니다.");
    }

    private static String tableQuery(Class<?> clazz) {
        return String.format("create table %s", clazz.getSimpleName());
    }
}
