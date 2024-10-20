package persistence.sql.meta;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnFieldsTest {

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

    }

    @Test
    @DisplayName("임의의 Class의 Column Clause 가져오기")
    void getColumnClause() {
        String expected = "id, nick_name, old, email";
        assertThat(new ColumnFields(ColumnFieldTest.DummyPerson.class).getColumnClause()).isEqualTo(expected);
    }

    @Test
    @DisplayName("임의의 Class의 Value Clause 가져오기")
    void getValueClause() throws Exception {
        String expected = "1, hyeongyeong, 30, kohy0329@naver.com";
        assertThat(new ColumnFields(DummyPerson.class).valueClause(getCustomPerson())).isEqualTo(expected);
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
}
