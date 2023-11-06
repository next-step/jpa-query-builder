package persistence.sql.dml;

import persistence.sql.vo.type.DatabaseType;

public class ValueClause {
    private final Object object;
    private final DatabaseType databaseType;

    public ValueClause(Object object, DatabaseType databaseType) {
        this.object = object;
        this.databaseType = databaseType;
    }

    public Object getObject() {
        return object;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }
}
