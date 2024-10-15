package persistence.sql.dml.query;

import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteByIdQueryBuilderTest {
    private final DeleteByIdQueryBuilder sut = new DeleteByIdQueryBuilder();

    @Test
    void testDeleteById() {
        final String sql = sut.build(Person.class);

        assertThat(sql).isEqualTo("DELETE FROM users WHERE id = ?;");
    }
}
