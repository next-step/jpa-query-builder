package database;

public interface Database {

    void execute(String sql);

    <T> T executeQueryForObject(Class<T> clazz, String sql);
}
