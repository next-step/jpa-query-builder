package utils;

import static persistence.sql.dml.DataLanguage.*;

public class CustomStringBuilder {

    private final StringBuilder sb;

    public CustomStringBuilder() {
        this.sb = new StringBuilder();
    }

    public CustomStringBuilder append(String str) {
        sb.append(str);

        if (str.lastIndexOf(" ") != str.length() - 1) {
            sb.append(" ");
        }

        return this;
    }

    public CustomStringBuilder appendWithoutSpace(String str) {
        sb.append(str);
        return this;
    }

    public String toString() {
        if (sb.lastIndexOf(" ") == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public String toStringWithoutSpace() {
        return sb.toString();
    }

    public static String toInsertColumnsClause(String tableName, String columnNames) {
        return new CustomStringBuilder()
                .append(INSERT.getName())
                .append(tableName)
                .appendWithoutSpace(LEFT_PARENTHESIS.getName())
                .appendWithoutSpace(columnNames)
                .append(RIGHT_PARENTHESIS.getName())
                .toStringWithoutSpace();
    }

    public static String toInsertValuesClause(String values) {
        return new CustomStringBuilder()
                .append(VALUES.getName())
                .appendWithoutSpace(LEFT_PARENTHESIS.getName())
                .appendWithoutSpace(values)
                .appendWithoutSpace(RIGHT_PARENTHESIS.getName())
                .appendWithoutSpace(SEMICOLON.getName())
                .toStringWithoutSpace();
    }

    public static String toDeleteStatement(String tableName, String columnNames, String values) {
        return new CustomStringBuilder()
                .append(DELETE.getName())
                .append(FROM.getName())
                .append(tableName)
                .append(WHERE.getName())
                .append(columnNames)
                .append(EQUALS.getName())
                .appendWithoutSpace(values)
                .appendWithoutSpace(SEMICOLON.getName())
                .toStringWithoutSpace();
    }

    public static String toFindAllStatement(String tableName, String columnNames) {
        return new CustomStringBuilder()
                .append(SELECT.getName())
                .append(columnNames)
                .append(FROM.getName())
                .appendWithoutSpace(tableName)
                .appendWithoutSpace(SEMICOLON.getName())
                .toStringWithoutSpace();
    }

    public static String toFindByIdStatement(String tableName, String columnNames, String idColumnName, String id) {
        return new CustomStringBuilder()
                .append(SELECT.getName())
                .append(columnNames)
                .append(FROM.getName())
                .append(tableName)
                .append(WHERE.getName())
                .append(idColumnName)
                .append(EQUALS.getName())
                .appendWithoutSpace(id)
                .appendWithoutSpace(SEMICOLON.getName())
                .toStringWithoutSpace();
    }

    public static String toCreateStatement(String tableName, String columnNames) {
        return new CustomStringBuilder()
                .append(CREATE.getName())
                .append(tableName)
                .appendWithoutSpace(LEFT_PARENTHESIS.getName())
                .appendWithoutSpace(columnNames)
                .appendWithoutSpace(RIGHT_PARENTHESIS.getName())
                .appendWithoutSpace(SEMICOLON.getName())
                .toStringWithoutSpace();
    }

    public static String toDropStatement(String tableName) {
        return new CustomStringBuilder()
                .append(DROP.getName())
                .appendWithoutSpace(tableName)
                .appendWithoutSpace(SEMICOLON.getName())
                .toStringWithoutSpace();
    }

}
