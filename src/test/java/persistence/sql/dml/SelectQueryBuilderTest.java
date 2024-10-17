package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectQueryBuilderTest {
    @Test
    void findAll() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class);
        String expectedQuery = "SELECT * FROM users;";
        String actualQuery = selectQueryBuilder.findAll(Person.class);

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    void findById() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class);
        String expectedQuery = "SELECT * FROM users WHERE id = 1;";
        String actualQuery = selectQueryBuilder.findById(Person.class, 1L);

        assertEquals(expectedQuery, actualQuery);
    }
}
