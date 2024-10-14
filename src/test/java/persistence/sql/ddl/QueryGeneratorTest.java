package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryGeneratorTest {
    @Test
    void create() {
        final QueryGenerator queryGenerator = new QueryGenerator();
        assertEquals(expected(), queryGenerator.create());
    }

    private String expected() {
        return """
                CREATE TABLE PERSON (
                    id BIGINT PRIMARY KEY,
                    name VARCHAR(255),
                    age INTEGER
                );""";
    }
}
