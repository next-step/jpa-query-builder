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
    void nullValueTest() {
        H2QueryBuilderDML h2QueryBuilderDML = new H2QueryBuilderDML();
        Person person = 이름이_없는_사람_객체(1L, 29, "qwerty@naver.com", 0);

        String insertQuery = h2QueryBuilderDML.insert(person);

        Assertions.assertThat(insertQuery).isEqualTo("insert into users (nick_name, old, email) values (NULL, 29, 'qwerty@naver.com');");
    }

    @Test
    @DisplayName("테이블 이름 값 확인하기")
    void tableNameCheck() {
        //given
        Person person = 완벽한_사람_객체(1L, "장현준", 29, "qwerty@naver.com", 0);

        //when
        TableName tableName = new TableName(person.getClass());

        //then
        Assertions.assertThat(tableName.getName()).isEqualTo("users");
    }

    @Test
    @DisplayName("findAll 쿼리 정상 테스트")
    void fidAllTest() {
        //given
        H2QueryBuilderDML h2QueryBuilderDML = new H2QueryBuilderDML();
        Person person = 완벽한_사람_객체(1L, "장현준", 29, "qwerty@naver.com", 0);

        //when
        String findAllResult = h2QueryBuilderDML.findAll(person);

        //then
        Assertions.assertThat(findAllResult).isEqualTo("select id, nick_name, old, email from users;");
    }

    @Test
    @DisplayName("findById 쿼리 정상 테스트")
    void findByIdTest() {
        //given
        H2QueryBuilderDML h2QueryBuilderDML = new H2QueryBuilderDML();
        Person person = 완벽한_사람_객체(1L, "장현준", 29, "qwerty@naver.com", 0);

        //when
        String findByIdResult = h2QueryBuilderDML.findById(person);

        //then
        Assertions.assertThat(findByIdResult).isEqualTo("select id, nick_name, old, email from users where id = 1;");
    }

    @Test
    @DisplayName("delete 쿼리 정상 테스트")
    void deleteTest() {
        //given
        H2QueryBuilderDML h2QueryBuilderDML = new H2QueryBuilderDML();
        Person person = 완벽한_사람_객체(1L, "장현준", 29, "qwerty@naver.com", 0);

        //when
        String deleteResult = h2QueryBuilderDML.delete(person);

        //then
        Assertions.assertThat(deleteResult).isEqualTo("delete from users where id = 1 and nick_name = '장현준' and old = 29 and email = 'qwerty@naver.com';");
    }

}