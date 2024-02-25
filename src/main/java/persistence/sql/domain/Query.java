package persistence.sql.domain;

import java.sql.Connection;

public class Query {

    private final String sql;

    private final DatabaseTable table;

    public Query(String sql, DatabaseTable table) {
        this.sql = sql;
        this.table = table;
    }

    public String getSql() {
        return sql;
    }

    public DatabaseTable getTable() {
        return table;
    }
}
