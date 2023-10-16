package persistence.sql.ddl;

import static java.lang.String.format;

public class CreateQueryBuilder<T> extends QueryBuilder<T> {

    private static final String DEFAULT_CREATE_TABLE_COLUMNS_FORMAT = "(%s)";

    private final EntityColumns entityColumns;
    private final Direct direct;

    public CreateQueryBuilder(Class<T> entityClass) {
       this(entityClass, new H2Direct());
    }

    public CreateQueryBuilder(Class<T> entityClass, Direct direct) {
        super(entityClass);
        this.direct = direct;
        entityColumns = new EntityColumns(entityFields, direct);
    }

    public String create() {
        return format(direct.createTablePreFix(tableName))
                + format(DEFAULT_CREATE_TABLE_COLUMNS_FORMAT, entityColumns.createColumnsQuery());
    }
}
