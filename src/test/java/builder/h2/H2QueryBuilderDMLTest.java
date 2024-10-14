package builder.h2;

import builder.QueryBuilderDML;
import builder.h2.dml.H2QueryBuilderDML;
import entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
- insert 쿼리 문자열 생성하기
- findAll 쿼리 문자열 생성하기
- findById 쿼리 문자열 생성하기
- deleteById 쿼리 문자열 생성하기
- deleteAll 쿼리 문자열 생성한다.
*/
public class H2QueryBuilderDMLTest {

    @DisplayName("Insert 쿼리 문자열 생성하기")
    @Test
    void buildInsertTest() {
        //given
        QueryBuilderDML queryBuilderDML = new H2QueryBuilderDML();

        Person person = new Person(1L, "sangki", 29, "test@test.com", 1);

        //when, then
        assertThat(queryBuilderDML.buildInsertQuery(person))
                .isEqualTo("INSERT INTO users (id, nick_name, old, email) VALUES (1, 'sangki', 29, 'test@test.com');");
    }

    @DisplayName("findAll 쿼리 문자열 생성하기")
    @Test
    void buildFindAllTest() {
        //given
        QueryBuilderDML queryBuilderDML = new H2QueryBuilderDML();

        //when, then
        assertThat(queryBuilderDML.buildFindAllQuery(Person.class))
                .isEqualTo("SELECT id, nick_name, old, email FROM users;");
    }

    @DisplayName("findById 쿼리 문자열 생성하기")
    @Test
    void buildFindByIdTest() {
        //given
        QueryBuilderDML queryBuilderDML = new H2QueryBuilderDML();

        //when, then
        assertThat(queryBuilderDML.buildFindByIdQuery(Person.class, 1))
                .isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 1;");
    }

    @DisplayName("findById 쿼리 문자열 생성할시 id가 String이면 작은따옴표로 묶어준다.")
    @Test
    void buildFindByIdStringTest() {
        //given
        QueryBuilderDML queryBuilderDML = new H2QueryBuilderDML();

        //when, then
        assertThat(queryBuilderDML.buildFindByIdQuery(Person.class, "sangki"))
                .isEqualTo("SELECT id, nick_name, old, email FROM users WHERE id = 'sangki';");
    }

    @DisplayName("deleteById 쿼리 문자열 생성한다.")
    @Test
    void buildDeleteByIdTest() {
        //given
        QueryBuilderDML queryBuilderDML = new H2QueryBuilderDML();

        //when, then
        assertThat(queryBuilderDML.buildDeleteByIdQuery(Person.class, "sangki"))
                .isEqualTo("DELETE FROM users WHERE id = 'sangki';");
    }

    @DisplayName("deleteAll 쿼리 문자열 생성한다.")
    @Test
    void buildDeleteTest() {
        //given
        QueryBuilderDML queryBuilderDML = new H2QueryBuilderDML();

        //when, then
        assertThat(queryBuilderDML.buildDeleteQuery(Person.class))
                .isEqualTo("DELETE FROM users;");
    }
}
