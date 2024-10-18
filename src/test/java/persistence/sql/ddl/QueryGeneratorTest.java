package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryGeneratorTest {
    @DisplayName("클래스 정보를 바탕으로 CREATE TABLE 쿼리를 생성한다.")
    @Test
    void create() {
        final QueryGenerator queryGenerator = new QueryGenerator(new H2Dialect());
        assertEquals(expected(), queryGenerator.create(Person.class));
    }

    @DisplayName("클래스 정보를 바탕으로 DROP TABLE 쿼리를 생성한다.")
    @Test
    void drop() {
        final QueryGenerator queryGenerator = new QueryGenerator(new H2Dialect());
        assertEquals("DROP TABLE IF EXISTS USERS CASCADE;", queryGenerator.drop(Person.class));
    }

    private String expected() {
        return """
                CREATE TABLE USERS (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    nick_name VARCHAR(255),
                    old INTEGER,
                    email VARCHAR(255) NOT NULL);""";
    }
}
