package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindAllQueryBuilderTest {

    @Test
    @DisplayName("Person Entity 를 위한 findAll 쿼리를 생성한다.")
    void build() {
        String expected = "SELECT"
                + " id, nick_name, old, email"
                + " FROM users;";
        String actual = new FindAllQueryBuilder<>(
                Person.class
        ).build();
        assertThat(actual).isEqualTo(expected);
    }
}
