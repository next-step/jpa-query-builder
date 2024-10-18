package persistence.sql.dml.query;

import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteByIdQueryBuilderTest {
    private final DeleteByIdQueryBuilder queryBuilder = new DeleteByIdQueryBuilder();

    @Test
    void testDeleteById() {
        Person person = new Person(1L, "John", 30, "", 1);
        final String query = queryBuilder.build(person);

        assertThat(query).isEqualTo("DELETE FROM users WHERE id = 1;");
    }
}
