package persistence.sql.ddl.processor;

import jakarta.persistence.Id;
import java.lang.reflect.Field;

public class FieldProcessor {

    private static FieldProcessor instance = null;
    private static final String SPACE = " ";

    private FieldProcessor() {
    }

    public static synchronized FieldProcessor getInstance() {
        if (instance == null) {
            instance = new FieldProcessor();
        }
        return instance;
    }


    public String process(Field field) {
        StringBuilder builder = new StringBuilder();

        builder.append(getColumnName(field));
        builder.append(SPACE);
        builder.append(getSqlType(field));
        builder.append(getConstraints(field));

        return builder.toString();
    }

    private String getColumnName(Field field) {
        return field.getName();
    }

    private String getSqlType(Field field) {
        Class<?> type = field.getType();
        if (type.equals(String.class)) {
            return "VARCHAR";
        } else if (type.equals(Integer.class)) {
            return "INTEGER";
        } else if (type.equals(Long.class)) {
            return "BIGINT";
        } else {
            throw new IllegalArgumentException("지원하지 않은 타입입니다.: " + type);
        }
    }

    private String getConstraints(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return " PRIMARY KEY";
        } else {
            return "";
        }
    }
}
