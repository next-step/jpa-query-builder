package persistence.sql.dml.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2FindAllQueryTest {

    @Test
    @DisplayName("Person Entity 를 위한 findAll 쿼리를 생성한다.")
    void findAll() {
        String expected = "SELECT"
                + " id, nick_name, old, email"
                + " FROM users";
        assertThat(
                H2FindAllQuery.build(Person.class)
        ).isEqualTo(expected);
    }
}
