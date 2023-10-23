package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.sql.QueryBuilder;

public class DeleteQueryBuilder extends QueryBuilder {
    public DeleteQueryBuilder(Dialect dialect) {
        super(dialect);
    }
}
