package persistence.sql.ddl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MyFieldTest {

    private static final Long id = 1L;
    private static final int age = 10;
    private static final String name = "my name";
    private static final String email = "my@email.com";

    private static Person person = new Person(id, name, age, email);;
    private static Class<? extends Person> personClass = person.getClass();

    @MethodSource
    @ParameterizedTest
    void createMyField(MyField myField, String expectedName, String expectedType, boolean expectedPk) {
        assertAll(
            () -> assertThat(myField.getName()).isEqualTo(expectedName),
            () -> assertThat(myField.getType()).isEqualTo(expectedType),
            () -> assertThat(myField.isPk()).isEqualTo(expectedPk)
        );
    }

    static Stream<Arguments> createMyField() throws NoSuchFieldException {
        return Stream.of(
            Arguments.of(
                new MyField(personClass.getDeclaredField("id")),
                "id",
                "BIGINT",
                true
            ),
            Arguments.of(
                new MyField(personClass.getDeclaredField("name")),
                "name",
                "VARCHAR",
                false
            ),
            Arguments.of(
                new MyField(personClass.getDeclaredField("age")),
                "age",
                "INT",
                false
            ),
            Arguments.of(
                new MyField(personClass.getDeclaredField("email")),
                "email",
                "VARCHAR",
                false
            )
        );
    }
}