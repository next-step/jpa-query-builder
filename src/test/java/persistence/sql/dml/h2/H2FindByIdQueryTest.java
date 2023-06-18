package persistence.sql.dml.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2FindByIdQueryTest {

    @Test
    @DisplayName("Person Entity 를 위한 findById 쿼리를 생성한다.")
    void findById() {
        String expected = "SELECT"
                + " id, nick_name, old, email"
                + " FROM users"
                + " WHERE id = 1";
        assertThat(
                H2FindByIdQuery.build(Person.class, 1)
        ).isEqualTo(expected);
    }
}
