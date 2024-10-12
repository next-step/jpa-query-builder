package persistence.sql.ddl;

import model.DDLColumns;
import model.TableName;

public class H2CreateQueryBuilder implements CreateQueryBuilder{

    private static final String SPACE = " ";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";

    private final Class<?> clazz;

    public H2CreateQueryBuilder(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("class가 존재하지 않습니다.");
        }
        this.clazz = clazz;
    }

    public String makeQuery() {
        TableName tableName = new TableName(clazz);

        DDLColumns ddlColumns = new DDLColumns(clazz.getDeclaredFields());
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE");
        sqlStringBuilder.append(SPACE);
        sqlStringBuilder.append(tableName.getValue());
        sqlStringBuilder.append(LEFT_PARENTHESIS);
        sqlStringBuilder.append(ddlColumns.makeColumnsDDL());
        sqlStringBuilder.append(RIGHT_PARENTHESIS);
        return sqlStringBuilder.toString();
    }
}
