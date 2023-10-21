package persistence.sql;

import persistence.sql.dbms.DbmsStrategy;
import persistence.sql.entitymetadata.model.EntityColumn;
import persistence.sql.entitymetadata.model.EntityTable;

public abstract class DbmsQueryBuilder<E> implements QueryBuilder<E> {
    protected DbmsStrategy dbmsStrategy;
    protected EntityTable<E> entityTable;

    protected DbmsQueryBuilder(DbmsStrategy dbmsStrategy, Class<E> entity) {
        this.dbmsStrategy = dbmsStrategy;
        this.entityTable = new EntityTable<>(entity);
    }

    protected String createTableNameDefinition() {
        return dbmsStrategy.defineTableName(entityTable);
    }

    protected String createColumnNameDefinition(EntityColumn<E, ?> entityColumn) {
        return dbmsStrategy.defineColumnName(entityColumn);
    }

    protected String createColumnTypeDefinition(EntityColumn<E, ?> entityColumn) {
        return dbmsStrategy.defineColumnType(entityColumn);
    }
}
