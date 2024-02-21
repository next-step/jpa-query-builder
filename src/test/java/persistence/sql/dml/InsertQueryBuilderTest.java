package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {

    @Test
    @DisplayName("InsertQuery를 만들 수 있다.")
    void buildInsertQuery() {
        //given
        Person person = new Person(1L, "name", 1, "email", 1);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();

        //when
        String query = insertQueryBuilder.build(person);

        //then
        assertThat(query).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('name', 1, 'email')");
    }
}
