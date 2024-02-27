package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.Table;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.converter.H2ColumnConverter;
import persistence.sql.dml.model.DMLColumn;

import static org.assertj.core.api.Assertions.assertThat;

class InsertQueryBuilderTest {

    private InsertQueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        final Table table = new Table();
        final DMLColumn column = new DMLColumn(new H2ColumnConverter());
        queryBuilder = new InsertQueryBuilder(table, column);
    }

    @Test
    void insertQueryTest() {
        final var person = new Person("name", 10, "a@a.com");
        final var expected = "INSERT INTO users (id, nick_name, old, email) VALUES (null, 'name', 10, 'a@a.com');";

        final var actual = queryBuilder.query(Person.class, person);

        assertThat(actual).isEqualTo(expected);
    }

}
