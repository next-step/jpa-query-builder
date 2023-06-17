package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DropQueryTest {

    @Test
    @DisplayName("Person Entity 를 위한 drop 쿼리를 생성한다.")
    void createDropQuery() {
        final String expected = "DROP TABLE IF EXISTS users";
        final String actual = new DropQuery<>(
                Person.class
        ).build();
        assertThat(actual).isEqualTo(expected);
    }
}
