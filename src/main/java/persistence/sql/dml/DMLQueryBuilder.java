package persistence.sql.dml;

import persistence.sql.model.Table;

public class DMLQueryBuilder {

    private final Table table;

    public DMLQueryBuilder(Table table) {
        this.table = table;
    }

    public String buildInsertQuery(Object instance) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(table, instance);
        return insertQueryBuilder.build();
    }

    public String buildFindAllQuery() {
        FindAllQueryBuilder findAllQueryBuilder = new FindAllQueryBuilder(table);
        return findAllQueryBuilder.build();
    }

    public String buildFindByIdQuery(Object instance) {
        FindByIdQueryBuilder findByIdQueryBuilder = new FindByIdQueryBuilder(table, instance);
        return findByIdQueryBuilder.build();
    }

    public String buildDeleteQuery() {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(table);
        return deleteQueryBuilder.build();
    }
}
