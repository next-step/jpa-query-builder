package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.domain.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectQueryBuilderTest {


    @Test
    @DisplayName("Person 객체로 Select(findAll) Query 만들기")
    void findAllQuery() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String findAllQuery = selectQueryBuilder.findAll(Person.class);

        assertEquals(findAllQuery, "select id, nick_name, old, email FROM users");
    }

    @Test
    @DisplayName("Person 객체로 Select(findById) Query 만들기")
    void findByIdQuery() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String findByIdQuery = selectQueryBuilder.findById(Person.class, 1L);

        assertEquals(findByIdQuery, "select id, nick_name, old, email FROM users where id = 1");
    }
}
