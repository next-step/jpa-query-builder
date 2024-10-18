package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import persistence.domain.Person;

class DropTableQueryTest {

    @Test
    void generateQuery() {
        DropTableQuery dropTableQuery = new DropTableQuery(Person.class);
        String expected = "DROP TABLE IF EXISTS users;";
        String actual = dropTableQuery.generateQuery();
        assertEquals(expected, actual);
    }

}
