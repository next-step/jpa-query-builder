package persistence.sql.dml;

import persistence.sql.domain.*;

import java.util.List;

public class DeleteQueryBuilder implements DeleteQueryBuild {

    private static final String DELETE_TEMPLATE = "delete %s where %s;";

    @Override
    public <T> Query delete(T entity) {
        DatabaseTable table = new DatabaseTable(entity);

        Where where = Where.from(table.getName());
        table.getAllColumns().forEach(column-> where.and(Condition.equal(column)));

        String sql = String.format(DELETE_TEMPLATE, where.getTableName(), where.getWhereClause());

        return new Query(sql, table);
    }
}
