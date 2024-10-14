package persistence.sql.dml;

import persistence.sql.dialect.Dialect;

public class DmlQueryBuilder {
    private final Dialect dialect;

    public DmlQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String getInsertQuery(Object entityObject) {
        return null;
    }
}
