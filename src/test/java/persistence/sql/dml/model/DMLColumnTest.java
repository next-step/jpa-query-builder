package persistence.sql.dml.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.DummyPerson;

import static org.assertj.core.api.Assertions.assertThat;

class DMLColumnTest {

    private DMLColumn column;

    @BeforeEach
    void setUp() {
        column = new DMLColumn(DummyPerson.of());
    }

    @Test
    @DisplayName("Field 객체가 가지는 Entity 의 필드명들을 조회한다.")
    void fieldsTest() {
        final var expected = "id, nick_name, old, email";

        final var actual = column.fields();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Value 객체가 가지는 Entity 의 값들을 조회한다.")
    void valuesTest() {
        final var expected = "1, 'name', 10, 'a@a.com'";

        final var actual = column.values();

        assertThat(actual).isEqualTo(expected);
    }
}
