package persistence.sql.ddl.generator;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultInsertDMLGeneratorTest {
    @Test
    void 아이디가_없는_insert_DML을_생성한다() {
        Person person = new Person(null, "soora", 10, "soora@naver.com", 5);
        DefaultInsertDMLGenerator generator = new DefaultInsertDMLGenerator();

        String dml = generator.generate(person);

        assertThat(dml).isEqualTo("INSERT INTO users (nick_name,old,email) values ('soora','10','soora@naver.com');");
    }

    @Test
    void 아이디가_있는_insert_DML을_생성한다() {
        Person person = new Person(15L, "soora", 10, "soora@naver.com", 5);
        DefaultInsertDMLGenerator generator = new DefaultInsertDMLGenerator();

        String dml = generator.generate(person);

        assertThat(dml).isEqualTo("INSERT INTO users (id,nick_name,old,email) values ('15','soora','10','soora@naver.com');");
    }
}
