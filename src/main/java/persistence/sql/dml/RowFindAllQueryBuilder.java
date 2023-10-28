package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.TableSQLMapper;

public class RowFindAllQueryBuilder extends QueryBuilder {

    public RowFindAllQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Class<?> clazz) {
        return "SELECT " +
            String.join(", ", TableSQLMapper.getAllEscapedColumnNamesOfTable(clazz)) +
            " FROM " +
            TableSQLMapper.getTableName(clazz) +
            ";";
    }
}
