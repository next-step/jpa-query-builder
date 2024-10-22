package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultUpdateDMLGeneratorTest {
    @Test
    void update_DML을_생성한다() {
        Person person = new Person(1L, "soora", 10, "soora@naver.com", 5);
        DefaultUpdateDMLGenerator generator = new DefaultUpdateDMLGenerator();

        String dml = generator.generate(person);

        assertThat(dml).isEqualTo("UPDATE users SET id = '1',nick_name = 'soora',old = '10',email = 'soora@naver.com' WHERE id = '1';");
    }
}