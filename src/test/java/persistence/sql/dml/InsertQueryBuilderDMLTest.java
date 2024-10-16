package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.sample.Person;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


class InsertQueryBuilderDMLTest {

    @Test
    @DisplayName("Person class에 대한 Insert Query 생성")
    void generateInsertQuery() throws Exception {

        InsertQueryBuilderDML insertQueryBuilderDML = InsertQueryBuilderDML.getInstance();
        String expected = "insert into users (id, nick_name, old, email) values (1, hyeongyeong, 30, kohy0329@naver.com);";

        Person person = getCustomPerson();
        assertThat(insertQueryBuilderDML.getQuery(person)).isEqualTo(expected);
    }

    private Person getCustomPerson() throws Exception{
        Person person = new Person();

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
