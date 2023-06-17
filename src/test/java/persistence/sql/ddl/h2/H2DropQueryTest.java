package persistence.sql.ddl.h2;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2DropQueryTest {

    @Test
    @DisplayName("Person Entity 를 위한 drop 쿼리를 생성한다.")
    void dropQuery() {
        final String expected = "DROP TABLE IF EXISTS users";
        assertThat(
                H2DropQuery.build(Person.class)
        ).isEqualTo(expected);
    }
}
