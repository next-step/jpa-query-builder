package persistence.sql.dml;

import persistence.sql.model.Table;

public class DMLQueryBuilder {

    private final Table table;
    private final Object entity;

    public DMLQueryBuilder(Table table, Object entity) {
        this.table = table;
        this.entity = entity;
    }

    public String buildInsertQuery() {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(table, entity);
        return insertQueryBuilder.build();
    }
}
