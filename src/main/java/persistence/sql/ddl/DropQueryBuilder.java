package persistence.sql.ddl;

import persistence.dialect.Dialect;
import persistence.meta.EntityMeta;

public class DropQueryBuilder<T> extends QueryBuilder<T> {
    public DropQueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        super(entityMeta, dialect);
    }

    public String drop() {
        return dialect.dropTable(entityMeta.getTableName());
    }
}
