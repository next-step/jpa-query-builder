package persistence.sql.ddl.utils;

public class LastColumnChecker {
    public static boolean isLastColumn(int currentColumnIndex, int columnCount) {
        return currentColumnIndex == columnCount - 1;
    }
}
