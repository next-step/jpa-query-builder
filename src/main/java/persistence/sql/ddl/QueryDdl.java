package persistence.sql.ddl;

public class QueryDdl {

    public static <T> String create(Class<T> tClass) throws NullPointerException {
        return CreateTable.create(tClass);
    }

    public static <T> String drop(Class<T> tClass) throws NullPointerException {
        return DropTable.drop(tClass);
    }
}
