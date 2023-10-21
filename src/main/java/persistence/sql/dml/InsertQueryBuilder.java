package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.common.ColumnUtils;
import persistence.sql.ddl.*;

import static java.lang.String.format;

public class InsertQueryBuilder{
    private final static String INSERT_COMMAND = "INSERT INTO %s (%s) VALUES %s;";

    private QueryValidator queryValidator;

    private final Table table;

    private final Values values;

    public InsertQueryBuilder(QueryValidator queryValidator, Object insertObject) {
        this.queryValidator = queryValidator;
        queryValidator.checkIsEntity(insertObject.getClass());
        this.table = new Table(insertObject.getClass());
        this.values = ColumnUtils.convertObjectToValues(insertObject);
    }

    public String buildQuery() {
        return format(INSERT_COMMAND, table.getName(), values.columnsClause(), "(" +  values.valueClause() + ")");
    }
}
