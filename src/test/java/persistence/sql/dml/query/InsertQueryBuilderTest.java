package persistence.sql.dml.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

public class InsertQueryBuilderTest {

    @Test
    @DisplayName("Should build insert users query")
    void shouldBuildInsertUsersQuery() {
        InsertQueryBuilder queryBuilder = new InsertQueryBuilder();
        Person person = new Person(1L, "john_doe", 30, "chanho0912@gmail.com", 1);

        String query = queryBuilder.build(person);

        System.out.println(query);
    }
}
