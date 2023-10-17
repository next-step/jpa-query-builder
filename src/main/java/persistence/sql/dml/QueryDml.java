package persistence.sql.dml;

public class QueryDml {

    public static <T> String insert(T t) {
        return InsertQuery.create(t);
    }
}
