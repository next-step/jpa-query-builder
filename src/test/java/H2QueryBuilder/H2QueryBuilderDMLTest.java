package H2QueryBuilder;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.Person;

import static H2QueryBuilder.fixtures.BuilderDMLFixtures.완벽한_사람_객체;
import static H2QueryBuilder.fixtures.BuilderDMLFixtures.이름이_없는_사람_객체;

class H2QueryBuilderDMLTest {
    @Test
    @DisplayName("INSERT query 정상 케이스")
    void happyCaseTest() {
        H2QueryBuilderDML h2QueryBuilderDML = new H2QueryBuilderDML();
        Person person = 완벽한_사람_객체(1L, "장현준", 29, "qwerty@naver.com", 0);
        String insertQuery = h2QueryBuilderDML.insert(person);

        Assertions.assertThat(insertQuery).isEqualTo("insert into users (nick_name, old, email) values ('장현준', 29, 'qwerty@naver.com');");
    }

    @Test
    @DisplayName("INSERT query NULL 값 확인")
    void NullValueTest() {
        H2QueryBuilderDML h2QueryBuilderDML = new H2QueryBuilderDML();
        Person person = 이름이_없는_사람_객체(1L, 29, "qwerty@naver.com", 0);

        String insertQuery = h2QueryBuilderDML.insert(person);

        Assertions.assertThat(insertQuery).isEqualTo("insert into users (nick_name, old, email) values (NULL, 29, 'qwerty@naver.com');");
    }

}