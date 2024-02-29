package persistence.sql.dml;

import persistence.sql.ddl.table.Table;
import persistence.sql.ddl.table.TableName;
import persistence.sql.dml.clause.ValueClause;
import persistence.sql.dml.clause.WhereClause;

public class DMLGenerator {

    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String FIND_QUERY = "SELECT * FROM %s%s;";
    private static final String DELETE_QUERY = "DELETE FROM %s%s;";

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

        return String.format(FIND_QUERY, tableName.getName(), "");
    }

    public String generateFindById(Long id) {
        TableName tableName = TableName.from(entity);

        String whereClause = String.format(" where id = %d", id);

        return String.format(FIND_QUERY, tableName.getName(), whereClause);
    }

    public String generateDelete(Object entity) {
        TableName tableName = TableName.from(this.entity);

        String whereClause = WhereClause.getWhereClause(entity);

        return String.format(DELETE_QUERY, tableName.getName(), " where " + whereClause);
    }
}
