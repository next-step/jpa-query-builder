package persistence.sql.ddl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import persistence.domain.Person;

class CreateTableQueryTest {

    @Test
    void generateQuery() {
        CreateTableQuery createTableQuery = new CreateTableQuery(Person.class);
        String sql = createTableQuery.generateQuery();
        String expected = "CREATE TABLE IF NOT EXISTS users (\n"
            + "id BIGINT PRIMARY KEY AUTO_INCREMENT,\n"
            + "nick_name VARCHAR(255) ,\n"
            + "old INT ,\n"
            + "email VARCHAR(255) NOT NULL\n"
            + ");";
        assertEquals(expected, sql);
    }

}
