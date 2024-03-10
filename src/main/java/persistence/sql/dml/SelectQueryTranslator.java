package persistence.sql.dml;

import persistence.sql.ddl.ColumnTranslator;
import persistence.sql.ddl.TableTranslator;

public class SelectQueryTranslator {
    private final ColumnTranslator columnTranslator;

    private final ColumnValueTranslator columnValueTranslator;

    private final TableTranslator tableTranslator;

    public SelectQueryTranslator(
        ColumnTranslator columnTranslator,
        ColumnValueTranslator columnValueTranslator,
        TableTranslator tableTranslator
    ) {
        this.columnTranslator = columnTranslator;
        this.columnValueTranslator = columnValueTranslator;
        this.tableTranslator = tableTranslator;
    }

    public String getSelectAllQuery(Class<?> entityClass) {
        return String.format(
            "SELECT %s FROM %s",
            columnTranslator.getColumnNamesClause(entityClass),
            tableTranslator.getTableNameFrom(entityClass)
        );
    }

    public String getSelectByIdQuery(Class<?> entityClass, Object id) {
        return String.format(
            "SELECT %s FROM %s WHERE %s = %s",
            columnTranslator.getColumnNamesClause(entityClass),
            tableTranslator.getTableNameFrom(entityClass),
            columnTranslator.getPrimaryKeyColumnName(entityClass),
            columnValueTranslator.getPrimaryKeyValueClauseFromEntityClassAndId(entityClass, id)
        );
    }

    public String getSelectCountQuery(Class<?> entityClass) {
        return String.format(
            "SELECT COUNT(%s) FROM %s",
            columnTranslator.getPrimaryKeyColumnName(entityClass),
            tableTranslator.getTableNameFrom(entityClass)
        );
    }
}
