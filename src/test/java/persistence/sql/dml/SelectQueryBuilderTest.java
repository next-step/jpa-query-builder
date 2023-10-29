package persistence.sql.dml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.entity.Person;

class SelectQueryBuilderTest {

    private Person person;

    private static final Long id = 1L;
    private static final Integer age = 10;
    private static final String name = "my name";
    private static final String email = "my@email.com";
    private static final Integer index = 0;

    @BeforeEach
    void setUp() {
        this.person = new Person(
            id, name, age, email, index
        );
    }

    @Test
    void findAllQueryBuilderTest() {
        String findAllQueryString = SelectQueryBuilder.findAllQueryString(Person.class);

        Assertions.assertThat(findAllQueryString).isEqualTo("select id, name, age, email from users");
    }

    @Test
    void findByIdQueryStringTest() {
        String byIdQueryString = SelectQueryBuilder.findByIdQueryString(person, id);

        Assertions.assertThat(byIdQueryString).isEqualTo("select id, name, age, email from users where id = 1;");
    }

}
