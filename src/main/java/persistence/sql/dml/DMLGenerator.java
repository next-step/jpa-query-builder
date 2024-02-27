package persistence.sql.dml;

import persistence.sql.ddl.table.Table;
import persistence.sql.dml.clause.ValueClause;

public class DMLGenerator {

    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUE (%s);";

    public String generateInsert(Object entity) {
        Table table = Table.from(entity.getClass());

        return String.format(INSERT_QUERY, table.getName(), table.getColumnsClause(), ValueClause.getValueClause(entity));
    }
}
