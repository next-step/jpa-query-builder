package persistence.sql.ddl;

public class QueryGenerator {
    public String create() {
        return """
                CREATE TABLE PERSON (
                    id BIGINT PRIMARY KEY,
                    name VARCHAR(255),
                    age INTEGER
                );
                """;
    }
}
