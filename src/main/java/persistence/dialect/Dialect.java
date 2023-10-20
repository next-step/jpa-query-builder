package persistence.dialect;

import jakarta.persistence.GenerationType;
import persistence.meta.ColumnType;

public abstract class Dialect {
    protected static final int DEFAULT_STRING_LENGTH = 255;

    public abstract String getVarchar(int length);

    public abstract String getInteger();

    public abstract String getBigInt();

    public abstract String getGeneratedType(GenerationType generationType);

    public abstract String notNull();

    public abstract String primaryKey(String columnName);

    public String getColumnType(ColumnType columnType) {
        if (columnType.isVarchar()) {
            return getVarchar(DEFAULT_STRING_LENGTH);
        }
        if (columnType.isInteger()) {
            return getInteger();
        }

        if (columnType.isBigInt()) {
            return getBigInt();
        }
        return "";
    }

    public String dropTable(String tableName) {
        return "DROP TABLE " + tableName;
    }

    public String insert(String tableName) {
        return "INSERT INTO " + tableName;
    }

    public String select(String fileNames) {
        return "SELECT " + fileNames  + " FROM ";
    }

    public String whereId(String name, String id) {
        return " WHERE " + name + " = " + id;
    }


    public String values(String value) {
        return " VALUES (" + value + ")";
    }

    public String createTablePreFix(String tableName) {
        return "CREATE TABLE " + tableName;
    }
}
