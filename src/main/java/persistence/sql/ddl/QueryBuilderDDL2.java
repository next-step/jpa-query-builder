package persistence.sql.ddl;

import model.DDLColumns;

import java.util.*;

public class QueryBuilderDDL2 {

    private static final String SPACE = " ";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";

    public String createTableSQL(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("class가 존재하지 않습니다.");
        }

        return assembleCreateTableSQL(clazz);
    }

    private String assembleCreateTableSQL(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("클래스가 존재하지 않습니다.");
        }

        DDLColumns ddlColumns = new DDLColumns(clazz.getDeclaredFields());
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE");
        sqlStringBuilder.append(SPACE);
        sqlStringBuilder.append(clazz.getSimpleName().toLowerCase());
        sqlStringBuilder.append(LEFT_PARENTHESIS);
        sqlStringBuilder.append(ddlColumns.makeColumnsDDL());
        sqlStringBuilder.append(RIGHT_PARENTHESIS);
        return sqlStringBuilder.toString();
    }

}
