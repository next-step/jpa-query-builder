package persistence.sql.ddl;

public class DropQueryBuilder<T> extends QueryBuilder<T>{
    private final Direct direct;

    public DropQueryBuilder(Class<T> entityClass) {
        this(entityClass, new H2Direct());

    }
    public DropQueryBuilder(Class<T> entityClass, Direct direct) {
        super(entityClass);
        this.direct = direct;
    }

    public String drop() {
        return direct.dropTable(tableName);
    }
}
