package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.entity.PersonFixtures;
import persistence.sql.dbms.Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class InsertDMLQueryBuilderTest {

    @Test
    void build() {
        Person person = PersonFixtures.fixture(1L, "name", 20, "asdf@asdf.com");

        InsertDMLQueryBuilder<Person> dmlQueryBuilder = new InsertDMLQueryBuilder<>(Dialect.H2, person);

        assertThat(dmlQueryBuilder.build()).isEqualTo("INSERT INTO USERS (ID, NICK_NAME, OLD, EMAIL) VALUES (1, 'name', 20, 'asdf@asdf.com');");
    }
}
