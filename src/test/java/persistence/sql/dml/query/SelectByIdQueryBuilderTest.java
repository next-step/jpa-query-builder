package persistence.sql.dml.query;

import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectByIdQueryBuilderTest {
    private final SelectByIdQueryBuilder sut = new SelectByIdQueryBuilder();

    @Test
    void testSelectById() {
        final String sql = sut.build(Person.class, 1L);

        assertThat(sql).isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1;");
    }

}
