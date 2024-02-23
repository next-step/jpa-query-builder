package persistence.sql.dml.builder;

import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {
    @Test
    void insertQueryBuilder() {
        Person person = new Person("hoon25", 20, "hoon25@gmail.com", 0);
        assertThat(new InsertQueryBuilder().generateSQL(person))
                .isEqualTo("insert into users (id, nick_name, old, email) values (DEFAULT, 'hoon25', 20, 'hoon25@gmail.com')");
    }
}
