package persistence.sql.ddl;

import jakarta.persistence.Entity;
import persistence.sql.ddl.model.DDLColumn;
import persistence.sql.ddl.model.TableName;

public class H2CreateQueryBuilder implements CreateQueryBuilder{

    private static final String SPACE = " ";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String RIGHT_PARENTHESIS = ")";

    private final Class<?> clazz;

    public H2CreateQueryBuilder(Class<?> clazz) {
        ExceptionUtil.requireNonNull(clazz);

        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 클래스가 아닙니다.");
        }

        this.clazz = clazz;
    }

    public String makeQuery() {
        TableName tableName = new TableName(clazz);

        DDLColumn ddlColumns = new DDLColumn(clazz.getDeclaredFields());
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
