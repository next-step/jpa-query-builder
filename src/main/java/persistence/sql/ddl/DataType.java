package persistence.sql.ddl;

public enum DataType {
    String,
    Long,
    Integer,
    ;

    DataType() {
    }

    public static String getDBType(String dataType) {
        if (dataType.equals(DataType.String.name())) {
            return "varchar(255)";
        }
        if (dataType.equals(DataType.Long.name())) {
            return "bigint";
        }
        if (dataType.equals(DataType.Integer.name())) {
            return "int";
        }
        throw new IllegalArgumentException("Not Supported DataType");
    }
}
