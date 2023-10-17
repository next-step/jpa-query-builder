package persistence.sql.ddl;

import persistence.dialect.Dialect;
import persistence.vender.dialect.H2Dialect;

public class DropQueryBuilder<T> extends QueryBuilder<T>{
    private final Dialect direct;

    public DropQueryBuilder(Class<T> entityClass) {
        this(entityClass, new H2Dialect());

    }
    public DropQueryBuilder(Class<T> entityClass, Dialect direct) {
        super(entityClass);
        this.direct = direct;
    }

    public String drop() {
        return direct.dropTable(tableName);
    }
}
