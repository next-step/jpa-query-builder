package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.Table;
import persistence.sql.dml.builder.FindAllQueryBuilder;
import persistence.sql.dml.converter.H2ColumnConverter;
import persistence.sql.dml.model.DMLColumn;

import static org.assertj.core.api.Assertions.assertThat;

class FindAllQueryBuilderTest {

    private FindAllQueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        final Table table = new Table();
        final DMLColumn column = new DMLColumn(new H2ColumnConverter());
        queryBuilder = new FindAllQueryBuilder(table, column);
    }

    @Test
    void findAllQueryTest() {
        final var expected = "SELECT (id, nick_name, old, email) FROM users;";

        final var actual = queryBuilder.query(Person.class);

        assertThat(actual).isEqualTo(expected);
    }
}
