package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InsertQueryBuilderTest {

    @Test
    public void testInsertQuery() {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
        Person person = new Person();
        person.setName("2xample");
        person.setAge(30);
        person.setEmail("2xample@gmail.com");

        String expectedQuery = "INSERT INTO users (nick_name, old, email) VALUES ('2xample', 30, '2xample@gmail.com');";
        String actualQuery = insertQueryBuilder.insert(person);

        assertEquals(expectedQuery, actualQuery);
    }
}