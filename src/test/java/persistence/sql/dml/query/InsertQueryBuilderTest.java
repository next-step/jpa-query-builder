package persistence.sql.dml.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class InsertQueryBuilderTest {

    @Test
    @DisplayName("Should build insert users query")
    void shouldBuildInsertUsersQuery() {
        InsertQueryBuilder queryBuilder = new InsertQueryBuilder();
        Person person = new Person(1L, "john_doe", 30, "chanho0912@gmail.com", 1);

        String query = queryBuilder.build(person);

        assertThat(query).isEqualTo( "INSERT INTO users (id, nick_name, old, email) VALUES (1, 'john_doe', 30, 'chanho0912@gmail.com');");
    }
}
