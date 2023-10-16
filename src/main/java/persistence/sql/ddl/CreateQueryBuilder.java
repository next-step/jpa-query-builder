package persistence.sql.ddl;

import static java.lang.String.format;

public class CreateQueryBuilder<T> extends QueryBuilder<T> {
    private static final String DEFAULT_CREATE_TABLE_FORMAT = "CREATE TABLE %s ";
    private static final String DEFAULT_CREATE_TABLE_COLUMNS_FORMAT = "(%s)";

    private final EntityColumns entityColumns;

    public CreateQueryBuilder(Class<T> entityClass) {
        super(entityClass);
        entityColumns = new EntityColumns(entityFields);
    }

    public String create() {
        return format(DEFAULT_CREATE_TABLE_FORMAT, tableName)
                + format(DEFAULT_CREATE_TABLE_COLUMNS_FORMAT, entityColumns.createColumnsQuery());
    }
}
