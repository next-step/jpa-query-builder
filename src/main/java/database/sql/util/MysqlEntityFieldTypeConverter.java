package database.sql.util;

public class MysqlEntityFieldTypeConverter implements EntityFieldTypeConverter {
    @Override
    public String convert(Class<?> type, Integer columnLength) {
        switch (type.getName()) {
            case "java.lang.Long":
                return "BIGINT";
            case "java.lang.String":
                return "VARCHAR(" + columnLength + ")";
            case "java.lang.Integer":
                return "INT";
            default:
                throw new RuntimeException("Cannot convert type: " + type.getName());
        }
    }
}
