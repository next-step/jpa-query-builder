package persistence.sql.dml;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.Table;
import persistence.sql.dml.builder.FindByIdQueryBuilder;
import persistence.sql.dml.converter.H2ColumnConverter;
import persistence.sql.dml.model.DMLColumn;

import static org.assertj.core.api.Assertions.assertThat;

class FindByIdQueryBuilderTest {

    private FindByIdQueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        final Table table = new Table();
        final DMLColumn column = new DMLColumn(new H2ColumnConverter());
        queryBuilder = new FindByIdQueryBuilder(table, column);
    }

    @Test
    void findByIdQueryTest() {
        final var expected = "SELECT (id, nick_name, old, email) FROM users WHERE id = 1;";

        final var actual = queryBuilder.query(Person.class, 1L);

        assertThat(actual).isEqualTo(expected);
    }
}
