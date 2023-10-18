package persistence.sql.ddl;

import persistence.dialect.Dialect;
import persistence.meta.EntityMeta;
import persistence.vender.dialect.H2Dialect;

public abstract class QueryBuilder<T> {
    protected final EntityMeta entityMeta;
    protected final Dialect dialect;

    protected QueryBuilder(EntityMeta entityMeta) {
        this(entityMeta, new H2Dialect());
    }

    protected QueryBuilder(EntityMeta entityMeta, Dialect dialect) {
        this.dialect = dialect;
        this.entityMeta = entityMeta;
    }

}
