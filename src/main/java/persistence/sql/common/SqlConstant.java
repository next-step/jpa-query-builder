package persistence.sql.common;

public class SqlConstant {
    public static final String CREATE_TABLE_START = "CREATE TABLE IF NOT EXISTS %s (";
    public static final String CREATE_TABLE_END = ")";
    public static final String CRETE_TABLE_COMMA = ",";
    public static final String CREATE_TABLE_ID_COLUMN = "id INT AUTO_INCREMENT PRIMARY KEY";
    public static final String CREATE_TABLE_VARCHAR_COLUMN_NULLABLE = "%s VARCHAR(30) NULL";
    public static final String CREATE_TABLE_VARCHAR_COLUMN_NOT_NULL = "%s VARCHAR(30) NOT NULL";
    public static final String CREATE_TABLE_COLUMN_INT = "%s INT";
    public static final String COLUMN_ID = "id";
}
