package persistence.sql.dml.model;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FieldTest {

    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field(Person.class);
    }

    @Test
    @DisplayName("Entity 객체에서 데이터베이스로 관리되는 필드명들을 조회한다.")
    void getEntityFieldNamesTest() {
        final var expected = "id, nick_name, old, email";

        final var actual = field.getEntityFieldClause();

        assertThat(actual).isEqualTo(expected);
    }

}
