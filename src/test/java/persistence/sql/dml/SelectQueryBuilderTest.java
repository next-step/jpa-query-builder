package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.domain.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectQueryBuilderTest {


    @Test
    @DisplayName("Person 객체로 Select(findAll) Query 만들기")
    void createTableQuery() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String findAllQuery = selectQueryBuilder.findAll(Person.class);

        assertEquals(findAllQuery, "select id, nick_name, old, email FROM users");
    }


}
