package persistence.sql.ddl;

import static java.lang.String.format;

import persistence.dialect.Dialect;
import persistence.vender.dialect.H2Dialect;

public class CreateQueryBuilder<T> extends QueryBuilder<T> {

    private static final String DEFAULT_CREATE_TABLE_COLUMNS_FORMAT = "(%s)";

    private final EntityColumns entityColumns;
    private final Dialect direct;

    public CreateQueryBuilder(Class<T> entityClass) {
       this(entityClass, new H2Dialect());
    }

    public CreateQueryBuilder(Class<T> entityClass, Dialect direct) {
        super(entityClass);
        this.direct = direct;
        entityColumns = new EntityColumns(entityFields, direct);
    }

    public String create() {
        return format(direct.createTablePreFix(tableName))
                + format(DEFAULT_CREATE_TABLE_COLUMNS_FORMAT, entityColumns.createColumnsQuery());
    }
}
