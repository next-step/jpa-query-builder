package persistence.sql.dml.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

class SelectAllQueryBuilderTest {
    private final SelectAllQueryBuilder queryBuilder = new SelectAllQueryBuilder();

    @Test
    @DisplayName("Should build select all query")
    void shouldBuildSelectAllQuery() {
        String query = queryBuilder.build(Person.class);

        assertThat(query).isEqualTo("SELECT * FROM users;");
    }
}
