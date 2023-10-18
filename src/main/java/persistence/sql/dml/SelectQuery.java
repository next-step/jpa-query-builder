package persistence.sql.dml;

import persistence.sql.common.Table;

public class SelectQuery extends Table {
    private static final String DEFAULT_SELECT_COLUMN_QUERY = "SELECT %s FROM %s";

    public <T> SelectQuery(Class<T> tClass) {
        super(tClass);
    }

    public static <T> String create(Class<T> tClass, String methodName) {
        isEntity(tClass);

        return new SelectQuery(tClass).combine(methodName);
    }

    public String combine(String methodName) {
        return parseFiled(methodName);
    }

    private String parseFiled(String methodName) {
        return String.format(DEFAULT_SELECT_COLUMN_QUERY, parseSelectFiled(methodName), getTableName());
    }

    private String parseSelectFiled(String methodName) {
        String filed = "";

        if(methodName.contains("All")) {
            filed = getColumnsWithComma();
        }

        return filed;
    }
}
