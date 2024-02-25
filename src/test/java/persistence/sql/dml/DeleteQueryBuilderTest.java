package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.Table;
import persistence.sql.dml.builder.DeleteQueryBuilder;
import persistence.sql.dml.converter.H2ColumnConverter;
import persistence.sql.dml.model.DMLColumn;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    private DeleteQueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        final Table table = new Table();
        final DMLColumn column = new DMLColumn(new H2ColumnConverter());
        queryBuilder = new DeleteQueryBuilder(table, column);
    }

    @Test
    void deleteQueryTest() {
        final var expected = "DELETE FROM users WHERE id = 1;";

        final var actual = queryBuilder.query(Person.class, 1L);

        assertThat(actual).isEqualTo(expected);
    }
}
