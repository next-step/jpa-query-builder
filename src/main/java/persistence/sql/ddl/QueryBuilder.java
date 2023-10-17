package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;

public class QueryBuilder {

    public <T> String create(Class<T> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(getTableName(clazz));
        sb.append(" (");

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            sb.append(getFieldName(field));
            sb.append(getAnnotationInfo(field));
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");

        return sb.toString();
    }

    private <T> String getTableName(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            return table.name();
        }

        return clazz.getSimpleName();
    }

    private String getFieldName(Field field) {
        StringBuilder sb = new StringBuilder();

        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            sb.append(column.name() == null || column.name().equals("") ? field.getName() : column.name());
            sb.append(" ");
            sb.append(getDataType(field.getType()));

            if (!column.nullable()) {
                sb.append(" NOT NULL");
            }
        } else {
            sb.append(field.getName());
            sb.append(" ");
            sb.append(getDataType(field.getType()));
        }

        return sb.toString();
    }

    private String getDataType(Class<?> type) {
        if (type == String.class) {
            return "VARCHAR(255)";
        } else if (type == int.class || type == Integer.class) {
            return "INT";
        } else if (type == long.class || type == Long.class) {
            return "BIGINT";
        }

        return null;
    }

    private String getAnnotationInfo(Field field) {
        StringBuilder sb = new StringBuilder();

        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);

            // TODO 나머지 GenerationType 추가 하기
            if (generatedValue.strategy().name().equals(GenerationType.IDENTITY.name())) {
                sb.append(" AUTO_INCREMENT");
            }
        }

        if (field.isAnnotationPresent(Id.class)) {
            sb.append(" PRIMARY KEY");
        }

        return sb.toString();
    }

}
