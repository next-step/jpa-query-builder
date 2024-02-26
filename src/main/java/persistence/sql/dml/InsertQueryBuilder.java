package persistence.sql.dml;

import persistence.sql.domain.DatabaseTable;
import persistence.sql.domain.Insert;
import persistence.sql.domain.Query;

public class InsertQueryBuilder implements InsertQueryBuild {

    private static final String INSERT_TEMPLATE = "insert into %s(%s) values(%s);";

    public <T> Query insert(T entity) {
        DatabaseTable table = new DatabaseTable(entity);
        Insert insert = new Insert(table);

        String sql = String.format(INSERT_TEMPLATE, table.getName(), insert.getColumnClause(), insert.getValueClause());

        return new Query(sql, table);
    }

}
