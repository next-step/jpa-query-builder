package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class QueryBuilder {

    public <T> String create(Class<T> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(clazz.getSimpleName());
        sb.append(" (");
        for (Field field : clazz.getDeclaredFields()) {
            sb.append(field.getName());
            sb.append(" ");
            sb.append(getDataType(field.getType()));
            sb.append(getAnnotationInfo(field));
            sb.append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");

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
        if (field.isAnnotationPresent(Id.class)) {
            return " PRIMARY KEY";
        }

        return "";
    }

}
