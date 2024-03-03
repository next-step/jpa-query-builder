package persistence.sql.dml.builder;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.model.Where;
import persistence.sql.model.Table;
import persistence.sql.dml.model.DMLColumn;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    private SelectQueryBuilder queryBuilder;

    @BeforeEach
    void setUp() {
        final Table table = new Table(Person.class);
        final DMLColumn column = new DMLColumn(Person.class);
        final Where where = new Where(Person.class);
        queryBuilder = new SelectQueryBuilder(table, column, where);
    }

    @Test
    void findAllQueryTest() {
        final var expected = "SELECT id, nick_name, old, email FROM users;";

        final var actual = queryBuilder.findAll().build();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByIdQueryTest() {
        final var expected = "SELECT id, nick_name, old, email FROM users WHERE id = 1;";

        final var actual = queryBuilder.findById(1L).build();

        assertThat(actual).isEqualTo(expected);
    }

}
