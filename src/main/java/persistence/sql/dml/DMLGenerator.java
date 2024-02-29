package persistence.sql.dml;

import persistence.sql.ddl.table.Table;
import persistence.sql.ddl.table.TableName;
import persistence.sql.dml.clause.ValueClause;

public class DMLGenerator {

    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String FIND_ALL_QUERY = "SELECT * FROM %s;";

    public String generateInsert(Object entity) {
        Table table = Table.from(entity.getClass());

        return String.format(INSERT_QUERY, table.getName(), table.getColumnsClause(), ValueClause.getValueClause(entity));
    }

    public String generateFindAll(Class<?> entity) {
        TableName tableName = TableName.from(entity);

        return String.format(FIND_ALL_QUERY, tableName.getName());
    }
}
