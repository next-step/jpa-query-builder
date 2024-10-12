package persistence.sql.ddl;

import persistence.sql.ddl.model.TableName;

public class H2DropQueryBuilder implements DropQueryBuilder {

    private static final String SPACE = " ";

    private static final String DROP_TABLE_DDL = "DROP TABLE IF EXISTS";


    private final Class<?> clazz;

    public H2DropQueryBuilder(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("class가 존재하지 않습니다.");
        }
        this.clazz = clazz;
    }

    public String makeQuery() {
        StringBuilder dropTableQueryStringBuilder = new StringBuilder();

        TableName tableName = new TableName(this.clazz);
        dropTableQueryStringBuilder.append(DROP_TABLE_DDL);
        dropTableQueryStringBuilder.append(SPACE);
        dropTableQueryStringBuilder.append(tableName.getValue());
        return dropTableQueryStringBuilder.toString();
    }
}
