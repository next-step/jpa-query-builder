package persistence.sql.ddl;

import persistence.meta.EntityMetaData;
import persistence.sql.dialect.Dialect;

public class QueryGenerator {
    private final Dialect dialect;

    public QueryGenerator(Dialect dialect) {
        this.dialect = dialect;
    }

    public String createTableQuery(EntityMetaData entityMetaData) {
        return "";
    }
}
