package persistence.sql.ddl;

public class QueryDdl {

    public static <T> String create(Class<T> tClass) {
        return CreateQuery.create(tClass);
    }

    public static <T> String drop(Class<T> tClass) {
        return DropQuery.drop(tClass);
    }
}
