package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.dialect.Dialect;

public class SelectQueryBuilder extends QueryBuilder {

    public SelectQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public String generateQuery(Class<?> clazz, Object id) {
        return clazz +
                whereClause(clazz, id);
    }

    @Override
    public String generateQuery(Class<?> clazz) {
        return String.format("SELECT %s FROM %s", generateColumnNames(clazz.getDeclaredFields()), generateTableName(clazz));
    }
}
