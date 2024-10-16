package persistence.sql.dml.query;

import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteByIdQueryBuilderTest {
    private final DeleteByIdQueryBuilder queryBuilder = new DeleteByIdQueryBuilder();

    @Test
    void testDeleteById() {
        final String query = queryBuilder.build(Person.class, 1L);

        assertThat(query).isEqualTo("DELETE FROM users WHERE id = 1;");
    }
}
