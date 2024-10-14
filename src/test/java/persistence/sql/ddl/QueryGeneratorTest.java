package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryGeneratorTest {
    @Test
    void create() {
        final QueryGenerator queryGenerator = new QueryGenerator();
        assertEquals(expected(), queryGenerator.create(Person.class));
    }

    private String expected() {
        return """
                CREATE TABLE PERSON (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    nick_name VARCHAR(255),
                    old INTEGER,
                    email VARCHAR(255) NOT NULL);""";
    }
}
