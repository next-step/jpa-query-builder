package persistence.sql.ddl;

public class CreateQueryBuilder {
    public String createTableQuery(Class<Person> personClass) {
        return "create table person (id BIGINT PRIMARY KEY, name VARCHAR, age INT)";
    }
}
