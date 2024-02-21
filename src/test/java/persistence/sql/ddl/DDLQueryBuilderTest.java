package persistence.sql.ddl;

import domain.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.converter.H2TypeConverter;
import persistence.sql.ddl.mapping.DDLQueryBuilder;
import persistence.sql.ddl.mapping.H2PrimaryKeyGenerationType;
import persistence.sql.ddl.mapping.QueryBuilder;
import persistence.sql.ddl.model.Column;
import persistence.sql.ddl.model.Table;

import static org.assertj.core.api.Assertions.assertThat;

class DDLQueryBuilderTest {

    private QueryBuilder queryBuilder;
    private Table table;
    private Column column;

    @BeforeEach
    void setUp() {
        table = new Table();
        column = new Column(new H2TypeConverter(), new H2PrimaryKeyGenerationType());
        queryBuilder = new DDLQueryBuilder(table, column);
    }

    @Test
    void createQueryTest() {
        final var expected = "CREATE TABLE users ( id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL );";

        final var actual = queryBuilder.create(Person.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void dropQueryTest() {
        final var expected = "DROP TABLE users;";

        final var actual = queryBuilder.drop(Person.class);

        assertThat(actual).isEqualTo(expected);
    }

}
