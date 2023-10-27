package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.SQLEscaper;
import persistence.sql.TableSQLMapper;

import java.lang.reflect.Field;

public class RowDeleteQueryBuilder extends QueryBuilder {
    public RowDeleteQueryBuilder(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Object object) {
        Field primaryKeyField = TableSQLMapper.getPrimaryKeyColumnField(object.getClass());
        String primaryKeyColumName = SQLEscaper.escapeNameByBacktick(TableSQLMapper.getColumnName(primaryKeyField));
        primaryKeyField.setAccessible(true);
        Object primaryKey;
        try {
            primaryKey = primaryKeyField.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return "DELETE FROM " +
            TableSQLMapper.getTableName(object.getClass()) +
            " WHERE " +
            primaryKeyColumName + " = " + primaryKey +
            ";";
    }
}
