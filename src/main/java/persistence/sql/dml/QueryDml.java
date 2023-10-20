package persistence.sql.dml;

public class QueryDml {

    public static <T> String insert(T t) {
        return InsertQuery.create(t);
    }

    public static <T> String select(Class<T> tClass, String methodName) {
        return SelectQuery.create(tClass, methodName);
    }

    public static <T> String select(Class<T> tClass, String methodName, Object... args) {
        return SelectQuery.create(tClass, methodName, args);
    }

    public static <T> String delete(Class<T> tClass, Object args) {
        return DeleteQuery.create(tClass, args);
    }
}
