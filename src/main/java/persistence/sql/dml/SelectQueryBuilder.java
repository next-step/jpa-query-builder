package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.sql.QueryBuilder;

public class SelectQueryBuilder extends QueryBuilder {
    public SelectQueryBuilder(Dialect dialect) {
        super(dialect);
    }
}
