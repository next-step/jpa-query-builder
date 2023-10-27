package persistence.sql;

import persistence.sql.dbms.Dialect;
import persistence.sql.entitymetadata.model.EntityColumn;
import persistence.sql.entitymetadata.model.EntityTable;

public abstract class DialectQueryBuilder<E> implements QueryBuilder<E> {
    protected Dialect dialect;
    protected EntityTable<E> entityTable;

    protected DialectQueryBuilder(Dialect dialect, Class<E> entity) {
        this.dialect = dialect;
        this.entityTable = new EntityTable<>(entity);
    }

    protected String createTableNameDefinition() {
        return dialect.defineTableName(entityTable);
    }

    protected String createColumnNameDefinition(EntityColumn<E, ?> entityColumn) {
        return dialect.defineColumnName(entityColumn);
    }

    protected String createColumnTypeDefinition(EntityColumn<E, ?> entityColumn) {
        return dialect.defineColumnType(entityColumn);
    }
}
