package persistence.sql.dml;

import persistence.sql.ddl.ColumnTranslator;
import persistence.sql.ddl.TableTranslator;

public class InsertQueryTranslator {
    private final ColumnTranslator columnTranslator;

    private final ColumnValueTranslator columnValueTranslator;

    private final TableTranslator tableTranslator;

    public InsertQueryTranslator(ColumnTranslator columnTranslator,
        ColumnValueTranslator columnValueTranslator, TableTranslator tableTranslator) {
        this.columnTranslator = columnTranslator;
        this.columnValueTranslator = columnValueTranslator;
        this.tableTranslator = tableTranslator;
    }

    public String getInsertQuery(Object entity) {
        Class<?> entityClass = entity.getClass();

        return String.format(
            "INSERT INTO %s (%s) VALUES (%s)",
            tableTranslator.getTableNameFrom(entityClass),
            columnTranslator.getColumnNamesClauseWithoutPrimaryKey(entityClass),
            columnValueTranslator.getColumnValueClause(entity)
        );
    }
}
