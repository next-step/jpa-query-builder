package persistence.sql.dml;

import persistence.sql.ddl.ColumnTranslator;
import persistence.sql.ddl.TableTranslator;

public class DeleteQueryTranslator {
    private final ColumnTranslator columnTranslator;

    private final ColumnValueTranslator columnValueTranslator;

    private final TableTranslator tableTranslator;

    public DeleteQueryTranslator(
        ColumnTranslator columnTranslator,
        ColumnValueTranslator columnValueTranslator,
        TableTranslator tableTranslator
    ) {
        this.columnTranslator = columnTranslator;
        this.columnValueTranslator = columnValueTranslator;
        this.tableTranslator = tableTranslator;
    }

    public String getDeleteAllQuery(Class<?> entityClass) {
        return String.format(
            "DELETE FROM %s",
            tableTranslator.getTableNameFrom(entityClass)
        );
    }

    public String getDeleteByIdQuery(Class<?> entityClass, Object id) {
        return String.format(
            "DELETE FROM %s WHERE %s = %s",
            tableTranslator.getTableNameFrom(entityClass),
            columnTranslator.getPrimaryKeyColumnName(entityClass),
            columnValueTranslator.getPrimaryKeyValueClauseFromEntityClassAndId(entityClass, id)
        );
    }

    public String getDeleteQueryFromEntity(Class<?> entityClass, Object entity) {
        return String.format(
            "DELETE FROM %s WHERE %s = %s",
            tableTranslator.getTableNameFrom(entityClass),
            columnTranslator.getPrimaryKeyColumnName(entityClass),
            columnValueTranslator.getPrimaryKeyValueClauseFromEntityClassAndEntityObject(entityClass, entity)
        );
    }
}
