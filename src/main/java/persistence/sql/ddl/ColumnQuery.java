package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ColumnQuery {
    private static final String SPACE = " ";
    private static final String PRIMARY_KEY = "PRIMARY KEY";

    private final String name;
    private final DataType type;
    private final boolean isPrimaryKey;

    public ColumnQuery(String name, DataType type, boolean isPrimaryKey) {
        this.name = name;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
    }

    public static ColumnQuery of(Field target) {
        String name = target.getName();
        DataType dataType = DataType.from(target.getType());
        boolean isPrimaryKey = isId(target);
        return new ColumnQuery(name, dataType, isPrimaryKey);
    }

    private static boolean isId(Field field) {
        return Arrays.stream(field.getDeclaredAnnotations())
                .map(Annotation::annotationType)
                .collect(Collectors.toList())
                .contains(Id.class);
    }

    public String toQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append(SPACE);
        stringBuilder.append(type.name());
        if (type.containsDefaultValue()) {
            stringBuilder.append(type.getDefaultValue());
        }
        if (isPrimaryKey) {
            stringBuilder.append(SPACE);
            stringBuilder.append(PRIMARY_KEY);
        }
        return stringBuilder.toString();
    }
}
