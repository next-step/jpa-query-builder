package persistence.sql;

import persistence.dialect.Dialect;
import persistence.dialect.h2.H2Dialect;
import persistence.exception.NoEntityException;
import persistence.meta.EntityMeta;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

public class QueryGenerator<T> {
    private final static Dialect DEFAULT_DIALECT = new H2Dialect();
    private final EntityMeta entityMeta;
    private final Dialect dialect;

    private QueryGenerator(EntityMeta entityMeta, Dialect dialect) {
        if (entityMeta == null) {
            throw new NoEntityException();
        }
        this.entityMeta = entityMeta;
        this.dialect = dialect;
    }
    private QueryGenerator(EntityMeta entityMeta) {
        this(entityMeta, DEFAULT_DIALECT);
    }

    public static <T> QueryGenerator <T> from(Class<T> tClass) {;
        return new QueryGenerator<>(new EntityMeta(tClass));
    }

    public static <T> QueryGenerator <T> from(EntityMeta entityMeta) {
        return new QueryGenerator<>(entityMeta);
    }

    public static <T> QueryGenerator<T> of(EntityMeta entityMeta, Dialect dialect) {
        return new QueryGenerator<>(entityMeta, dialect);
    }

    public String create() {
        return new CreateQueryBuilder<>(entityMeta, dialect).create();
    }

    public String drop() {
        return new DropQueryBuilder<>(entityMeta, dialect).drop();
    }

    public String insert(T object) {
        return new InsertQueryBuilder<>(entityMeta, dialect).insert(object);
    }

    public String delete(Object id) {
        return new DeleteQueryBuilder<>(entityMeta, dialect).delete(id);
    }

    public SelectQueryBuilder<T> select() {
        return new SelectQueryBuilder<>(entityMeta, dialect);
    }
}
