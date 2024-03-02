package persistence.sql.dml.builder;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.model.Table;
import persistence.sql.dml.model.DMLColumn;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    private DeleteQueryBuilder queryBuilder;
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("name", 10, "a@a.com");
        final Table table = new Table();
        final DMLColumn column = new DMLColumn(person);
        queryBuilder = new DeleteQueryBuilder(table, column);
    }

    @Test
    void deleteQueryByObjectTest() {
        final var expected = "DELETE FROM users WHERE nick_name = 'name' AND old = 10 AND email = 'a@a.com';";

        final var actual = queryBuilder.query(person);

        assertThat(actual).isEqualTo(expected);
    }

}
