package persistence.sql.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Function;

public class FieldConverter {

    private static final Map<Type, Function<String,String>> map = Map.of(
            String.class, fieldName -> String.format("%s VARCHAR(30)", fieldName),
            Integer.class, fieldName -> String.format("%s INT", fieldName)
    );

    public static final String SQL_CREATE_ID_COLUMN = "id INT AUTO_INCREMENT PRIMARY KEY";

    public static String getColumn(Field field) {
        if (field.getType().equals(Long.class) && field.getName().equals("id")) {
            return SQL_CREATE_ID_COLUMN;
        }
        return map.get(field.getType()).apply(field.getName());
    }
}
