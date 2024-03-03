package persistence.sql.dml.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.DummyPerson;

import static org.assertj.core.api.Assertions.assertThat;

class ValueTest {

    private Value value;

    @BeforeEach
    void setUp() {
        value = new Value(DummyPerson.of());
    }

    @Test
    @DisplayName("특정 Entity 객체가 가지고 있는 Value 들을 조회한다.")
    void getEntityValueClauseTest() {
        final var expected = "1, 'name', 10, 'a@a.com'";

        final var actual = value.getEntityValueClause();

        assertThat(actual).isEqualTo(expected);
    }

}
