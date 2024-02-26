package persistence.sql.dml;

import persistence.sql.model.Table;

public class DMLQueryBuilder {

    private final Table table;

    public DMLQueryBuilder(Table table) {
        this.table = table;
    }

    public String buildInsertQuery(Object entity) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(table, entity);
        return insertQueryBuilder.build();
    }

    public String buildFindAllQuery() {
        FindAllQueryBuilder findAllQueryBuilder = new FindAllQueryBuilder(table);
        return findAllQueryBuilder.build();
    }
}
