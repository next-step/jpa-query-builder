package persistence.sql.ddl;


public class H2DBDataType {
    public static String castType(Class<?> columnType) {
        return switch (columnType.getSimpleName()) {
            case "Long", "long" -> "BIGINT";
            case "Integer", "int" -> "INTEGER";
            case "String" -> "VARCHAR(255)";
            case "Boolean", "boolean" -> "BIT";
            case "Double", "double" -> "DOUBLE";
            case "Float", "float" -> "REAL";
            case "Date" -> "DATE";
            default -> throw new IllegalArgumentException("Unsupported data type: " + columnType.getName());
        };
    }
}
