package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DMLGeneratorTest {

    DMLGenerator dmlGenerator = new DMLGenerator();

    @Test
    void generateInsert() {
        // given
        Person person = new Person("name", 26, "email", 1);

        // when
        String result = dmlGenerator.generateInsert(person);

        // then
        assertThat(result).isEqualTo("INSERT INTO users (nick_name, old, email) VALUE (name, 26, email);");
    }

}