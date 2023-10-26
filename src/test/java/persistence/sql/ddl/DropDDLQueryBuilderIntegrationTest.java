package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.dbms.Dialect;
import persistence.testutils.H2TableMetaResultRow;
import persistence.testutils.TestQueryExecuteSupport;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DropDDLQueryBuilderIntegrationTest extends TestQueryExecuteSupport {

    @Test
    void executeDdlQuery() {

        // given
        jdbcTemplate.execute("DROP TABLE IF EXISTS PUBLIC.USERS;");
        createPersonTableAndAssertion();

        DropDDLQueryBuilder<Person> dropDDLQueryBuilder = new DropDDLQueryBuilder<>(Dialect.H2, Person.class);

        // when
        String dropQuery = dropDDLQueryBuilder.build();
        jdbcTemplate.execute(dropQuery.replace("DROP TABLE USERS", "DROP TABLE PUBLIC.USERS"));

        List<H2TableMetaResultRow> results = jdbcTemplate.query("SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE \n" +
                "FROM information_schema.columns \n" +
                "WHERE TABLE_SCHEMA = 'PUBLIC' AND table_name = 'USERS'\n" +
                ";", resultSet -> new H2TableMetaResultRow(
                resultSet.getString("TABLE_NAME"),
                resultSet.getString("COLUMN_NAME"),
                resultSet.getString("DATA_TYPE")
        ));

        assertThat(results).isEmpty();
    }

    private void createPersonTableAndAssertion() {
        CreateDDLQueryBuilder<Person> createDDLQueryBuilder = new CreateDDLQueryBuilder<>(Dialect.H2, Person.class);
        String createQuery = createDDLQueryBuilder.build();
        jdbcTemplate.execute(createQuery.replace("CREATE TABLE USERS", "CREATE TABLE PUBLIC.USERS"));

        List<H2TableMetaResultRow> results = jdbcTemplate.query("SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE \n" +
                "FROM information_schema.columns \n" +
                "WHERE TABLE_SCHEMA = 'PUBLIC' AND table_name = 'USERS'\n" +
                ";", resultSet -> new H2TableMetaResultRow(
                resultSet.getString("TABLE_NAME"),
                resultSet.getString("COLUMN_NAME"),
                resultSet.getString("DATA_TYPE")
        ));

        assertThat(results).containsExactly(
                new H2TableMetaResultRow("USERS", "ID", "BIGINT"),
                new H2TableMetaResultRow("USERS", "NICK_NAME", "CHARACTER VARYING"),
                new H2TableMetaResultRow("USERS", "OLD", "INTEGER"),
                new H2TableMetaResultRow("USERS", "EMAIL", "CHARACTER VARYING")
        );
    }
}
