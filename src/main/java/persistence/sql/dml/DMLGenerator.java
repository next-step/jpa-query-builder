package persistence.sql.dml;

import persistence.sql.ddl.table.Table;
import persistence.sql.ddl.table.TableName;
import persistence.sql.dml.clause.ValueClause;

public class DMLGenerator {

    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String FIND_QUERY = "SELECT * FROM %s;";

    private final Class<?> entity;

    public DMLGenerator(Class<?> entity) {
        this.entity = entity;
    }

    public String generateInsert(Object entity) {
        Table table = Table.from(entity.getClass());

        return String.format(INSERT_QUERY, table.getName(), table.getColumnsClause(), ValueClause.getValueClause(entity));
    }

    public String generateFindAll() {
        TableName tableName = TableName.from(entity);

        return String.format(FIND_QUERY, tableName.getName());
    }

    public String generateFindById(Long id) {
        TableName tableName = TableName.from(entity);

        String whereClause = String.format("%s where id = %d", tableName.getName(), id);

        return String.format(FIND_QUERY, whereClause);
    }
}
