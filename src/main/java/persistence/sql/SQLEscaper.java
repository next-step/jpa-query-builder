package persistence.sql;

public class SQLEscaper {
    public static String escapeNameByBacktick(String columnName) {
        return "`" + columnName + "`";
    }

    public static String escapeNameBySingleQuote(String columnName) {
        return "'" + columnName + "'";
    }
}
