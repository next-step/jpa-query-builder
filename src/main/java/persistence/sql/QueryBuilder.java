package persistence.sql;

import persistence.sql.entitymetadata.model.EntityTable;

public interface QueryBuilder<E> {
    String build(EntityTable<E> entityTable);
}
