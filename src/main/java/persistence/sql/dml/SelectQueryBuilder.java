package persistence.sql.dml;

import persistence.sql.domain.Condition;
import persistence.sql.domain.DatabaseTable;
import persistence.sql.domain.Query;
import persistence.sql.domain.Where;

public class SelectQueryBuilder implements SelectQueryBuild {

    private static final String FIND_ALL_TEMPLATE = "select * from %s;";
    private static final String FIND_WITH_CONDITION_TEMPLATE = "select * from %s where %s;";

    @Override
    public Query findAll(Class<?> entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String sql = String.format(FIND_ALL_TEMPLATE, table.getName());
        return new Query(sql, table);
    }

    @Override
    public Query findById(Class<?> entity, Object id) {
        if (id == null) {
            throw new IllegalArgumentException("database id can not be null");
        }
        DatabaseTable table = new DatabaseTable(entity);

        Condition condition = Condition.equal(table.getPrimaryColumn(), id);
        Where where = Where.from(table.getName())
                .and(condition);

        String sql = String.format(FIND_WITH_CONDITION_TEMPLATE, where.getTableName(), where.getWhereClause());
        return new Query(sql, table);
    }
}
