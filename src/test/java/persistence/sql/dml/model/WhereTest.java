package persistence.sql.dml.model;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.DummyPerson;

import static org.assertj.core.api.Assertions.assertThat;

class WhereTest {

    private Where where;
    private Value value;

    @BeforeEach
    void setUp() {
        final Person person = DummyPerson.of();
        where = new Where(person);
        value = new Value(person);
    }

    @Test
    @DisplayName("Entity 의 id 절 쿼리를 생성한다.")
    void getIdClauseTest() {
        final var expected = "id";

        final var actual = where.getIdClause();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Entity 에 해당하는 Where 절 쿼리를 생성한다.")
    void getEntityClauseTest() {
        final var expected = "id = 1 AND nick_name = 'name' AND old = 10 AND email = 'a@a.com'";

        final var actual = where.getEntityClause(value);

        assertThat(actual).isEqualTo(expected);
    }

}
