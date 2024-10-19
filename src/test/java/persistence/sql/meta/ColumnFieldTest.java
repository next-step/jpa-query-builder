package persistence.sql.meta;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnFieldTest {

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
    @DisplayName("임의의 Class에 정의된 Field 이름 가져오기")
    void getColumnFieldNames() {
        List<String> expected = Arrays.asList("id", "nick_name", "old", "email");
        assertThat(new ColumnFields(DummyPerson.class).getDeclaredFieldNames())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("임의의 Transient Annotation이 포함된 Class에 정의된 Field 이름 가져오기")
    void getColumnFieldNames_Transient() {
        List<String> expected = Arrays.asList("id", "nick_name", "old", "email");
        assertThat(new ColumnFields(DummyPerson2.class).getDeclaredFieldNames())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("임의의 Class의 Primary Column 가져오기")
    void getPrimary() {
        List<String> expected = Arrays.asList("id");
        assertThat(new ColumnFields(DummyPerson.class).getPrimary().stream().map(ColumnField::getName)).isEqualTo(expected);
    }

    @Test
    @DisplayName("임의의 Class의 Primary Column 가져오기 - id가 여러개 정의됨")
    void getPrimary_multiple() {
        List<String> expected = Arrays.asList("id", "nick_name");
        assertThat(new ColumnFields(DummyPerson2.class).getPrimary().stream().map(ColumnField::getName)).isEqualTo(expected);
    }

    @Test
    @DisplayName("임의의 Class의 Column Clause 가져오기")
    void getColumnClause() {
        String expected = "id, nick_name, old, email";
        assertThat(new ColumnFields(DummyPerson.class).getColumnClause()).isEqualTo(expected);
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
