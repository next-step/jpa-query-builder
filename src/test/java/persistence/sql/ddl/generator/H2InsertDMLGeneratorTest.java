package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.EntityFields;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class H2InsertDMLGeneratorTest {
    @Test
    void DML을_생성한다() {
        Person person = new Person(null, "soora", 10, "soora@naver.com", 5);
        H2InsertDMLGenerator generator = new H2InsertDMLGenerator();

        String dml = generator.generate(person);

        assertThat(dml).isEqualTo("INSERT INTO users (nick_name,old,email) values ('soora','10','soora@naver.com');");
    }
}
