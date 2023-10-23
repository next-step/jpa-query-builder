package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.TableFieldUtil;
import persistence.sql.TableQueryUtil;

import java.lang.reflect.Field;

public class RowDeleteQueryGenerator extends QueryBuilder {
    public RowDeleteQueryGenerator(Dialect dialect) {
        super(dialect);
    }

    @Override
    public String generateSQLQuery(Object object) throws IllegalAccessException {
        Field primaryKeyField = TableFieldUtil.getPrimaryKeyField(object.getClass());
        String primaryKeyColumName = TableFieldUtil.replaceNameByBacktick(TableFieldUtil.getColumnName(primaryKeyField));
        primaryKeyField.setAccessible(true);
        Object value = primaryKeyField.get(object);

        return "DELETE FROM " +
            TableQueryUtil.getTableName(object.getClass()) +
            " WHERE " +
            primaryKeyColumName + " = " + value +
            ";";
    }
}
