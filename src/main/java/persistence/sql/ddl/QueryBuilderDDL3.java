package persistence.sql.ddl;

import jakarta.persistence.Table;
import model.DDLColumns;

public class QueryBuilderDDL3 {

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
        sqlStringBuilder.append(getTableName(clazz));
        sqlStringBuilder.append(LEFT_PARENTHESIS);
        sqlStringBuilder.append(ddlColumns.makeColumnsDDL());
        sqlStringBuilder.append(RIGHT_PARENTHESIS);
        return sqlStringBuilder.toString();
    }

    private String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return clazz.getSimpleName().toLowerCase();
        }

        String name = clazz.getAnnotation(Table.class).name();
        if (name.isEmpty()) {
            return clazz.getSimpleName().toLowerCase();
        }
        return name;
    }

}
