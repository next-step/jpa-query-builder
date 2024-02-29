package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DMLGeneratorTest {

    DMLGenerator dmlGenerator = new DMLGenerator(Person.class);

    @Test
    void insert() {
        // given
        Person person = new Person("name", 26, "email", 1);

        // when
        String insert = dmlGenerator.generateInsert(person);

        // then
        assertThat(insert).isEqualTo("INSERT INTO users (nick_name, old, email) VALUES ('name', 26, 'email');");
    }

    @Test
    void findAll() {
        // given & when
        String result = dmlGenerator.generateFindAll();

        // then
        assertThat(result).isEqualTo("SELECT * FROM users;");
    }

    @Test
    void findById() {
        // given & when
        String result = dmlGenerator.generateFindById(1L);

        // then
        assertThat(result).isEqualTo("SELECT * FROM users where id = 1;");
    }

    @Test
    void generateDelete() {
        // given
        Person person = new Person("name", 26, "email", 1);

        // when
        String result = dmlGenerator.generateDelete(person);

        // then
        assertThat(result).isEqualTo("DELETE FROM users where nick_name = 'name' AND old = 26 AND email = 'email';");
    }
}
