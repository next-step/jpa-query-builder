package persistence.sql.dml.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectAllQueryBuilderTest {
    private final SelectAllQueryBuilder sut = new SelectAllQueryBuilder();

    @Test
    @DisplayName("Should build select all query")
    void shouldBuildSelectAllQuery() {
        String query = sut.build(Person.class);

        assertThat(query).isEqualTo("SELECT * FROM users;");
    }
}
