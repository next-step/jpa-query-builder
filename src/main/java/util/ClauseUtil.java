package util;

import persistence.sql.ddl.column.ColumnType;

public class ClauseUtil {

    private ClauseUtil() {
    }

    public static String addQuotesWhenRequire(Class<?> type, String value) {
        if (ColumnType.requireQuotes(type)) {
            return StringUtil.addStringOnBothSides(value, "'");
        }

        return value;
    }
}
