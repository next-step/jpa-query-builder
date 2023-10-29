package persistence.sql.dml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import persistence.sql.entity.Person;

class InsertQueryBuilderTest {

    private static final Long id = 1L;
    private static final String  name = "hiro";
    private static final Integer age = 21;
    private static final String email = "hi@email.com";
    private static final Integer index = 0;

    @Test
    void insertQueryBuilderTest() {
        Person person = new Person(id, name, age, email, index);

        String insertQueryString = InsertQueryBuilder.insertQueryString(person);

        Assertions.assertThat(insertQueryString)
            .isEqualTo("INSERT INTO users (id, name, age, email) values (1, 'hiro', 21, 'hi@email.com');");
    }
}
