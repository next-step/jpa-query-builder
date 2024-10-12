package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DropQueryBuilderTest {

    @Test
    @DisplayName("users table drop query 만들기")
    void dropTableQuery() {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder();
        String dropTableQuery = dropQueryBuilder.dropTableQuery(Person.class);

        assertEquals(dropTableQuery, "drop table users");

    }

}
