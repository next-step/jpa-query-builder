package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2DDLQueryBuilderTest {

    private final DDLQueryBuilder queryBuilder = new H2DDLQueryBuilder();

    @Test
    void personCreateQueryTest() {
        final var expected = "CREATE TABLE person ( id BIGINT NOT NULL PRIMARY KEY, name VARCHAR(255), age INTEGER );";

        final var actual = queryBuilder.create(Person.class);

        assertThat(actual).isEqualTo(expected);
    }

}
