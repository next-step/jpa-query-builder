package persistence.sql.ddl;

public class CreateQueryBuilder {
    public String createTableQuery(Class<?> clazz) {
        String tableName = clazz.getSimpleName();

        return String.format("create table %s (id BIGINT PRIMARY KEY, name VARCHAR, age INT)",
                tableName);
    }
}
