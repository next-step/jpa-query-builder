package persistence.sql.dml;

import domain.Person;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DeleteDDLQueryBuilderTest {
    @Test
    public void testDeleteById() {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
        String expectedQuery = "DELETE FROM users WHERE id = 1;";
        String actualQuery = deleteQueryBuilder.deleteById(Person.class, 1L);

        assertEquals(expectedQuery, actualQuery);
    }
    }