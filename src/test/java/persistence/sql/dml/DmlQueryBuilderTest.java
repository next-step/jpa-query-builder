package persistence.sql.dml;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DmlQueryBuilderTest {

    private final DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();

    @Test
    void should_create_insert_query() {
        Person person = new Person("cs", 29, "katd216@gmail.com", 1);

        String query = dmlQueryBuilder.insert(person);

        assertThat(query).isEqualTo("insert into users(nick_name,old,email) values('cs',29,'katd216@gmail.com');");
    }

    @Test
    void should_create_find_all_query() {
        String query = dmlQueryBuilder.findAll(Person.class);

        assertThat(query).isEqualTo("select * from users;");
    }

    @Test
    void should_create_find_by_id_query() {
        String query = dmlQueryBuilder.findById(Person.class, 1l);

        assertThat(query).isEqualTo("select * from users where id=1;");
    }

    @Test
    void should_throw_exception_when_id_is_null() {
        assertThatThrownBy(() -> dmlQueryBuilder.findById(Person.class, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("database id can not be null");
    }
}
