package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.PersonV3;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.mapping.*;

import java.sql.Types;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultDmlQueryBuilderTest {

    private final TableBinder tableBinder = new TableBinder();

    private final Dialect dialect = new H2Dialect();

    private final DmlQueryBuilder queryBuilder = new DefaultDmlQueryBuilder(dialect);

    @DisplayName("엔티티 객체를 받아 insert 쿼리문을 생성한다")
    @Test
    public void buildInsertQuery() throws Exception {
        // given
        final PersonV3 person = new PersonV3(1L, "name", 1, "email@domain.com", 0);
        final Table table = tableBinder.createTable(person);

        final Insert insert = new Insert(table);

        final String dml = "insert\n" +
                "into\n" +
                "    users\n" +
                "    (nick_name, old, email, id)\n" +
                "values\n" +
                "    ('name', 1, 'email@domain.com', default)";

        // when
        final String result = queryBuilder.buildInsertQuery(insert);

        // then
        assertThat(result).isEqualTo(dml);
    }

    @DisplayName("엔티티 클래스로 findAll 쿼리를 생성한다")
    @Test
    public void buildFindAllQuery() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final Table table = tableBinder.createTable(clazz);
        final Select select = new Select(table);

        final String dml = "select\n" +
                "    id, nick_name, old, email\n" +
                "from\n" +
                "    users";

        // when
        final String result = queryBuilder.buildSelectQuery(select);

        // then
        assertThat(result).isEqualTo(dml);
    }

    @DisplayName("엔티티 클래스로 findById 쿼리를 생성한다")
    @Test
    public void buildFindByIdQuery() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final Table table = tableBinder.createTable(clazz);
        final Column column = table.getColumn("id");
        final Value value = new Value(Long.class, Types.BIGINT, 1, "1");
        final List<Where> wheres = List.of(new Where(column, value, LogicalOperator.NONE, new ComparisonOperator(ComparisonOperator.Comparisons.EQ)));
        final Select select = new Select(table, wheres);

        final String dml = "select\n" +
                "    id, nick_name, old, email\n" +
                "from\n" +
                "    users\n" +
                "where\n" +
                "    id = 1";

        // when
        final String result = queryBuilder.buildSelectQuery(select);

        // then
        assertThat(result).isEqualTo(dml);
    }

    @DisplayName("엔티티 클래스로 deleteById 쿼리를 생성한다")
    @Test
    public void buildDeleteById() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final Table table = tableBinder.createTable(clazz);
        final Column column = table.getColumn("id");
        final Value value = new Value(Long.class, Types.BIGINT, 1, "1");
        final List<Where> wheres = List.of(new Where(column, value, LogicalOperator.NONE, new ComparisonOperator(ComparisonOperator.Comparisons.EQ)));
        final Delete delete = new Delete(table, wheres);

        final String dml = "delete\n" +
                "from\n" +
                "    users\n" +
                "where\n" +
                "    id = 1";

        // when
        final String result = queryBuilder.buildDeleteQuery(delete);

        // then
        assertThat(result).isEqualTo(dml);
    }

}