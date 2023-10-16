package persistence.sql.ddl;

public class DropQuery extends Query {
    private static final String DEFAULT_DROP_QUERY = "DROP TABLE %s";

    private <T> DropQuery(Class<T> tClass) {
        super(tClass);
    }

    public static <T> String drop(Class<T> tClass) throws NullPointerException {
        if (!isEntity(tClass)) {
            throw new NullPointerException();
        }

        return new DropQuery(tClass).combineQuery();
    }

    private String combineQuery() {
        return String.format(DEFAULT_DROP_QUERY, this.getTableName());
    }
}
