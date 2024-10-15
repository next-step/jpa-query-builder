package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class H2InsertQueryBuilderTest {

    @Test
    void 데이터_삽입() {
        final String name = "이름";
        final Integer age = 11;
        final String email = "email@test.com";

        Person person = new Person(name, age, email, null);
        InsertQueryBuilder insertQueryBuilder = new H2InsertQueryBuilder(person);

        String sql = insertQueryBuilder.makeQuery();
        assertThat(sql).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES (이름, 11, email@test.com)");
    }

}
