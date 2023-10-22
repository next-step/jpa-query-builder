package persistence.sql.ddl;

import persistence.sql.QueryBuilder;

import static java.lang.String.format;

public class DropQueryBuilder implements QueryBuilder {
    private static final String DROP_TABLE_COMMAND = "DROP TABLE %s;";

    private final QueryValidator queryValidator;

    private final Table table;

    public DropQueryBuilder(QueryValidator queryValidator, Class<?> clazz) {
        this.queryValidator = queryValidator;
        queryValidator.checkIsEntity(clazz);
        this.table = new Table(clazz);
    }

    @Override
    public String buildQuery() {
        return format(DROP_TABLE_COMMAND, table.getName());
    }
}
