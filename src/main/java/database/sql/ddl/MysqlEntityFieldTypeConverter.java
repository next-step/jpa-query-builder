package database.sql.ddl;

public class MysqlEntityFieldTypeConverter implements EntityFieldTypeConverter {
    @Override
    public String convert(Class<?> type) {
        switch (type.getName()) {
            case "java.lang.Long":
                return "BIGINT";
            case "java.lang.String":
                return "VARCHAR(100)";
            case "java.lang.Integer":
                return "INT";
            default:
                throw new RuntimeException("Cannot convert type: " + type.getName());
        }
    }
}
