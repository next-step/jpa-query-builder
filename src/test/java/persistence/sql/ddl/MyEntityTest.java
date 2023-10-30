package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.entity.Person;

class MyEntityTest {

    private Person person;

    private static final Long id = 1L;
    private static final int age = 10;
    private static final String name = "my name";
    private static final String email = "my@email.com";
    private static final Integer index = 0;


    @BeforeEach
    void setUp() {
        this.person = new Person(id, name, age, email, index);
    }

    @Test
    void createMyEntity() throws NoSuchFieldException {
        MyEntity myEntity = new MyEntity(person.getClass());
        List<MyField> myFields = myEntity.getMyFields();

        Class<? extends Person> personClass = person.getClass();

        MyField idField = new MyField(personClass.getDeclaredField("id"));
        MyField nameField = new MyField(personClass.getDeclaredField("name"));
        MyField ageField = new MyField(personClass.getDeclaredField("age"));
        MyField emailField = new MyField(personClass.getDeclaredField("email"));

        assertAll(
            () -> assertThat(myEntity.getTableName()).isEqualTo("users"),
            () -> assertThat(myFields).contains(idField, nameField, ageField, emailField)
        );
    }
}
