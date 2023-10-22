package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.TableQueryUtil;

public class RowFindAllQueryGenerator extends QueryBuilder {

    public RowFindAllQueryGenerator(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Object object) {
        return "SELECT " +
            TableQueryUtil.getSelectedColumns(object) +
            " FROM " +
            TableQueryUtil.getTableName(object.getClass()) +
            ";";
    }
}
