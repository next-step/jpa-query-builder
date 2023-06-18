package persistence.sql.dml.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2DeleteByIdQueryTest {

    @Test
    @DisplayName("Person Entity 를 위한 delete 쿼리를 생성한다.")
    void delete() {
        String expected = "DELETE FROM users"
                + " WHERE id = 1";
        assertThat(
                H2DeleteByIdQuery.build(Person.class, 1)
        ).isEqualTo(expected);
    }
}
