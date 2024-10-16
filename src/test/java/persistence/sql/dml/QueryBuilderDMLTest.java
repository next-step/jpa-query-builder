package persistence.sql.dml;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


class QueryBuilderDMLTest {

    @Table(name = "users")
    @Entity
    public class DummyPerson {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Column(name = "old")
        private Integer age;

        @Column(nullable = false)
        private String email;

        @Transient
        private Integer index;

    }

    @Table(name = "users")
    @Entity
    public class DummyPerson2 {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Id
        @Column(name = "nick_name")
        private String name;

        @Column(name = "old")
        private Integer age;

        @Column(nullable = false)
        private String email;

        @Transient
        private Integer index;

    }

    @Test
    @DisplayName("Person class에 대한 Insert Query 생성")
    void generateInsertQuery() throws Exception {

        QueryBuilderDML insertQueryBuilderDML = QueryBuilderDML.getInstance();
        String expected = "insert into users (id, nick_name, old, email) values (1, hyeongyeong, 30, kohy0329@naver.com)";

        DummyPerson person = getCustomPerson();
        assertThat(insertQueryBuilderDML.insert(person)).isEqualTo(expected);
    }

    private DummyPerson getCustomPerson() throws Exception{
        DummyPerson person = new DummyPerson();

        Field idField = person.getClass().getDeclaredField("id");
        Field nameField = person.getClass().getDeclaredField("name");
        Field ageField = person.getClass().getDeclaredField("age");
        Field emailField = person.getClass().getDeclaredField("email");

        idField.setAccessible(true);
        nameField.setAccessible(true);
        ageField.setAccessible(true);
        emailField.setAccessible(true);

        idField.set(person, 1L);
        nameField.set(person, "hyeongyeong");
        ageField.set(person, 30);
        emailField.set(person, "kohy0329@naver.com");

        return person;
    }

    private DummyPerson2 getCustomPerson2() throws Exception{
        DummyPerson2 person = new DummyPerson2();

        Field idField = person.getClass().getDeclaredField("id");
        Field nameField = person.getClass().getDeclaredField("name");
        Field ageField = person.getClass().getDeclaredField("age");
        Field emailField = person.getClass().getDeclaredField("email");

        idField.setAccessible(true);
        nameField.setAccessible(true);
        ageField.setAccessible(true);
        emailField.setAccessible(true);

        idField.set(person, 1L);
        nameField.set(person, "hyeongyeong");
        ageField.set(person, 30);
        emailField.set(person, "kohy0329@naver.com");

        return person;
    }

    @Test
    @DisplayName("입력받은 object의 findAll query 생성")
    void generateFindAllQuery() throws Exception {
        DummyPerson person = getCustomPerson();

        QueryBuilderDML queryBuilderDML = QueryBuilderDML.getInstance();
        String expected = "select id, nick_name, old, email from users";
        assertThat(queryBuilderDML.findAll(person)).isEqualTo(expected);
    }

    @Test
    @DisplayName("입력받은 object의 findById query 생성")
    void generateFindByIdQuery() throws Exception {
        DummyPerson person = getCustomPerson();

        QueryBuilderDML queryBuilderDML = QueryBuilderDML.getInstance();
        String expected = "select id, nick_name, old, email from users where id=1";
        assertThat(queryBuilderDML.findById(person)).isEqualTo(expected);
    }

    @Test
    @DisplayName("입력받은 object의 findById query 생성 - id가 여러 개 일 때")
    void generateFindByIdQuery_multipleIds() throws Exception {
        DummyPerson2 person = getCustomPerson2();

        QueryBuilderDML queryBuilderDML = QueryBuilderDML.getInstance();
        String expected = "select id, nick_name, old, email from users where id=1 and nick_name=hyeongyeong";
        assertThat(queryBuilderDML.findById(person)).isEqualTo(expected);
    }
}
