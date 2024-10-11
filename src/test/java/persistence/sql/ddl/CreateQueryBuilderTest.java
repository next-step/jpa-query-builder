package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateQueryBuilderTest {


    @Test
    @DisplayName("Person 객체로 Create Table Query 만들기")
    void createTableQuery() {

        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder();
        String createTableQuery = createQueryBuilder.createTableQuery(Person.class);

        assertEquals(createTableQuery, "create table Person (id BIGINT PRIMARY KEY, name VARCHAR, age INT)");
    }

}
