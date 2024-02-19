package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.domain.Person;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    @DisplayName("Person객체를 통해 insert를 구현한다.")
    @Test
    void dml_insert_create() {
        QueryBuilder queryBuilder = new QueryBuilder();
        Person person = new Person("simpson", 31, "qwe5507@gmail.com");

        String insertQuery = queryBuilder.createInsertQuery(person);

        String expected = String.format("insert into users (id, nick_name, old, email) values (default, 'simpson', 31, 'qwe5507@gmail.com')");
        assertThat(insertQuery).isEqualTo(expected);
    }

    @DisplayName("Person객체를 통해 findAll 기능을 구현한다.")
    @Test
    void dml_findAll_create() {
        QueryBuilder queryBuilder = new QueryBuilder();
        final Class<Person> personClass = Person.class;

        String findAllQuery = queryBuilder.createFindAll(personClass);

        String expected = "select id, nick_name, old, email from users";
        assertThat(findAllQuery).isEqualTo(expected);
    }
}