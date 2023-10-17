package persistence.sql.dml;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SelectQueryTest {

    @Test
    @DisplayName("")
    void findAll() {
        //given
        final String expectedQuery = "SELECT id, nick_name, old, email FROM users";
        final Person person = new Person(3L, "name", 3, "ee@n.com", 1);

        //when
        String query = SelectQuery.create(person, new Object() {
        }.getClass().getEnclosingMethod().getName());

        //then
        assertThat(query).isEqualTo(expectedQuery);
    }
}