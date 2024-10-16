package persistence.sql.dml.query;

import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

class SelectByIdQueryBuilderTest {
    private final SelectByIdQueryBuilder queryBuilder = new SelectByIdQueryBuilder();

    @Test
    void testSelectById() {
        final String query = queryBuilder.build(Person.class, 1L);

        assertThat(query).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1;");
    }

}
