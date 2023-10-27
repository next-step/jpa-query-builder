package jdbc;

import persistence.sql.dbms.Dialect;
import persistence.sql.entitymetadata.model.EntityColumn;
import persistence.sql.entitymetadata.model.EntityTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleEntityRowMapper<T> implements RowMapper<T> {
    private EntityTable<T> entityTable;
    private Dialect dialect;

    public SimpleEntityRowMapper(EntityTable<T> entityTable, Dialect dialect) {
        this.entityTable = entityTable;
        this.dialect = dialect;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        T entity = entityTable.createEntityInstance();

        for (EntityColumn<T, ?> column : entityTable.getColumns()) {
            String columnName = dialect.defineColumnName(column);
            column.setValue(entity, resultSet.getObject(columnName));
        }

        return entity;
    }
}
