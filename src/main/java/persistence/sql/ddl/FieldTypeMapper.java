package persistence.sql.ddl;

import java.lang.reflect.Field;

public class FieldTypeMapper {
    public String mapFieldTypeToSQLType(Field field) {
        String fieldType = field.getType().getSimpleName();
        return switch (fieldType) {
            case "String" -> "VARCHAR(255)";
            case "Integer" -> "INTEGER";
            case "Long" -> "BIGINT";
            default -> throw new IllegalArgumentException("Unsupported field type: " + fieldType);
        };
    }
}
