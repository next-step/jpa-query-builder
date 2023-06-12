package persistence.sql.ddl.mapping;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Column {

    private static final Map<Class<?>, String> JAVA_TO_JDBC_TYPE_MAPPING = new HashMap<>();

    static {
        JAVA_TO_JDBC_TYPE_MAPPING.put(Long.class, "bigint");
        JAVA_TO_JDBC_TYPE_MAPPING.put(Integer.class, "integer");
        JAVA_TO_JDBC_TYPE_MAPPING.put(String.class, "text");
    }

    private final Field field;

    public Column(Field field) {
        this.field = field;
    }

    public String createDdlString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s %s", field.getName(), JAVA_TO_JDBC_TYPE_MAPPING.get(field.getType())));

        if (isNotNull()) {
            builder.append(" not null");
        }

        return builder.toString();
    }

    private boolean isNotNull() {
        return field.isAnnotationPresent(Id.class);
    }
}
