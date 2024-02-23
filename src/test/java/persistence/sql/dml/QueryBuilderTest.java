package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.domain.Person;
import persistence.sql.dml.keygenerator.H2KeyGenerator;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    @DisplayName("Person객체를 통해 insert를 구현한다.")
    @Test
    void dml_insert_create() {
        Person person = new Person("simpson", 31, "qwe5507@gmail.com");
        QueryBuilder queryBuilder = new QueryBuilder(person.getClass());

        String insertQuery = queryBuilder.createInsertQuery(person, new H2KeyGenerator());

        String expected = String.format("insert into users (id, nick_name, old, email) values (default, 'simpson', 31, 'qwe5507@gmail.com')");
        assertThat(insertQuery).isEqualTo(expected);
    }

    @DisplayName("Person객체를 통해 findAll 기능을 구현한다.")
    @Test
    void dml_findAll_create() {
        Person person = new Person("simpson", 31, "qwe5507@gmail.com");
        QueryBuilder queryBuilder = new QueryBuilder(person.getClass());

        String findAllQuery = queryBuilder.createFindAllQuery();

        String expected = "select id, nick_name, old, email from users";
        assertThat(findAllQuery).isEqualTo(expected);
    }

    @DisplayName("Person객체를 통해 findById 기능을 구현한다.")
    @Test
    void dml_findById_create() {
        Person person = new Person(1L, "simpson", 31, "qwe5507@gmail.com");
        QueryBuilder queryBuilder = new QueryBuilder(person.getClass());

        String findByIdQuery = queryBuilder.createFindByIdQuery(1L);

        String expected = "select id, nick_name, old, email from users where id = 1L";
        assertThat(findByIdQuery).isEqualTo(expected);
    }

    @DisplayName("Person객체를 통해 delete를 구현한다.")
    @Test
    void dml_delete_create() {
        Person person = new Person(1L, "simpson", 31, "qwe5507@gmail.com");
        QueryBuilder queryBuilder = new QueryBuilder(person.getClass());

        String deleteQuery = queryBuilder.createDeleteQuery(person);

        String expected = "delete from users where id = 1L";
        assertThat(deleteQuery).isEqualTo(expected);
    }
}