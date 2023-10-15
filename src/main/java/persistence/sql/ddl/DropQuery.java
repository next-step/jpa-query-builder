package persistence.sql.ddl;

public class DropQuery extends Query {

    public static final String DEFAULT_DROP_QUERY = "DROP TABLE %s";

    private <T> DropQuery(Class<T> tClass) {
        super(tClass);
    }

    public static <T> DropQuery initDropQuery(Class<T> tClass) throws NullPointerException {
        if (!isEntity(tClass)) {
            throw new NullPointerException();
        }

        return new DropQuery(tClass);
    }

    public String dropQuery() {
        return String.format(DEFAULT_DROP_QUERY, this.getTableName());
    }
}
