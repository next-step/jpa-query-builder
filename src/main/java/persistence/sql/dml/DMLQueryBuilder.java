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

    public String buildFindByIdQuery(Object id) {
        FindByIdQueryBuilder findByIdQueryBuilder = new FindByIdQueryBuilder(table, id);
        return findByIdQueryBuilder.build();
    }

    public String buildDeleteAllQuery() {
        DeleteAllQueryBuilder deleteAllQueryBuilder = new DeleteAllQueryBuilder(table);
        return deleteAllQueryBuilder.build();
    }

    public String buildDeleteByIdQuery(Object id) {
        DeleteByIdQueryBuilder deleteByIdQueryBuilder = new DeleteByIdQueryBuilder(table, id);
        return deleteByIdQueryBuilder.build();
    }
}
