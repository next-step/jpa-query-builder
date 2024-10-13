package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.domain.Person;

import static org.junit.jupiter.api.Assertions.*;

class DeleteQueryBuilderTest {

    @Test
    @DisplayName("Person 객체로 Delete Query 만들기")
    void deleteQuery() {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
        String deleteQuery = deleteQueryBuilder.delete(Person.class, 1L);

        assertEquals(deleteQuery, "delete FROM users where ID = 1");
    }


}
