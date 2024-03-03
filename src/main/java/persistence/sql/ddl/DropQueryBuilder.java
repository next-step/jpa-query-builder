package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.dialect.Dialect;

public class DropQueryBuilder extends QueryBuilder {

    public DropQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateQuery(Class<?> clazz) {
        return "DROP TABLE " + generateTableName(clazz);
    }
}
