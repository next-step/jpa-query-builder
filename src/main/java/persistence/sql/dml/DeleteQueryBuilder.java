package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.dialect.Dialect;

public class DeleteQueryBuilder extends QueryBuilder {

    public DeleteQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    public String generateQuery(Class<?> clazz, Object id) {
        return String.format("%s %s", clazz, whereClause(clazz, id));
    }

    @Override
    public String generateQuery(Class<?> clazz) {
        return String.format("DELETE FROM %s", generateTableName(clazz));
    }
}
