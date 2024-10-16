package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectDDLQueryBuilderTest {
    @Test
    void findAll() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String expectedQuery = "SELECT * FROM users;";
        String actualQuery = selectQueryBuilder.findAll(Person.class);

        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    void findById() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String expectedQuery = "SELECT * FROM users WHERE id = 1;";
        String actualQuery = selectQueryBuilder.findById(Person.class, 1);

        assertEquals(expectedQuery, actualQuery);
    }
}
