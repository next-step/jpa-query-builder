package persistence.sql.ddl;

public class DropQueryBuilder<T> extends QueryBuilder<T>{
    private static final String DEFAULT_DROP_TABLE_FORMAT = "DROP TABLE %s";

    public DropQueryBuilder(Class<T> entityClass) {
        super(entityClass);
    }

    public String drop() {
        return String.format(DEFAULT_DROP_TABLE_FORMAT, tableName);
    }
}
