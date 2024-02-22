package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.dialect.Database;

import static org.assertj.core.api.Assertions.assertThat;

class InsertDmlTest {

    @DisplayName("Person 객체를 insert 쿼리로 변환한다.")
    @Test
    void testInsertDml() {
        InsertDml insertDml = new InsertDml();
        Person person = new Person("username", 50, "test@test.com", 1);
        String insert = insertDml.generate(person, Database.MYSQL);

        assertThat(insert).isEqualTo("insert into users (nick_name, old, email) values ('username', 50, 'test@test.com')");
    }

    @DisplayName("Person 객체를 insert 쿼리로 변환한다. - username과 index만 입력")
    @Test
    void testInsertDmlWhenSpecificField() {
        InsertDml insertDml = new InsertDml();
        Person person = new Person("username", 1);
        String insert = insertDml.generate(person, Database.MYSQL);

        assertThat(insert).isEqualTo("insert into users (nick_name, old, email) values ('username', null, null)");
    }
}
