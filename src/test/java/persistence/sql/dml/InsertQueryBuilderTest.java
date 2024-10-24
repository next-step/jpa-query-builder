package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsertQueryBuilderTest {

    @Test
    public void testInsertQuery() {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(Person.class);
        Person person = Person.of(null, "2xample", 30, "2xample@gmail.com", null);

        String expectedQuery = "INSERT INTO users (nick_name, old, email) VALUES ('2xample', 30, '2xample@gmail.com');";
        String actualQuery = insertQueryBuilder.insert(person);

        assertEquals(expectedQuery, actualQuery);
    }
}
