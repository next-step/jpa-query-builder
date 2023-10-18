package persistence.sql.ddl;

import persistence.dialect.Dialect;
import persistence.meta.EntityMeta;

public class DropQueryBuilder<T> extends QueryBuilder<T> {
    protected DropQueryBuilder(EntityMeta entityMeta) {
        super(entityMeta);
    }

    protected DropQueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        super(entityMeta, dialect);
    }

    public String drop() {
        return dialect.dropTable(entityMeta.getTableName());
    }
}
