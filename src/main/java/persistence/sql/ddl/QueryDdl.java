package persistence.sql.ddl;

public class QueryDdl {

    public static <T> String create(Class<T> tClass) throws NullPointerException {
        return CreateQuery.create(tClass);
    }

    public static <T> String drop(Class<T> tClass) throws NullPointerException {
        return DropQuery.drop(tClass);
    }
}
