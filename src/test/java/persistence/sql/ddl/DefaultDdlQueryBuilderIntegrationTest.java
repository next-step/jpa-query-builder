package persistence.sql.ddl;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.JdbcServerTest;
import persistence.sql.TestJdbcServerExtension;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.query.Query;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcServerTest
class DefaultDdlQueryBuilderIntegrationTest {

    private final Dialect dialect= new H2Dialect();
    private final DdlQueryBuilder queryBuilder = new DefaultDdlQueryBuilder(dialect);

    @AfterEach
    void tearDown() {
        final JdbcTemplate jdbcTemplate = TestJdbcServerExtension.getJdbcTemplate();

        List<String> tableNames = jdbcTemplate
                .query("SELECT table_name FROM information_schema.tables WHERE table_schema='PUBLIC'", resultSet -> resultSet.getString("table_name"));

        for (String tableName : tableNames) {
            jdbcTemplate.execute("DROP TABLE IF EXISTS " + tableName + " CASCADE");
        }
    }

    @DisplayName("Entity 객체 정보로 만든 Query 를 이용해 db 에 create ddl 쿼리를 날린다")
    @Test
    public void createTable() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final Query query = queryBuilder.generateQuery(clazz);
        final jdbc.JdbcTemplate jdbcTemplate = TestJdbcServerExtension.getJdbcTemplate();
        final String createQuery = queryBuilder.buildCreateQuery(query);

        // when
        jdbcTemplate.execute(createQuery);

        // then
        List<String> tableNames = jdbcTemplate
                .query("SELECT table_name FROM information_schema.tables WHERE table_schema='PUBLIC'", resultSet -> resultSet.getString("table_name"));
        assertThat(tableNames).hasSize(1)
                .containsExactlyInAnyOrder("USERS");
    }

    @DisplayName("Entity 객체 정보로 만든 Query 를 이용해 db 에 drop ddl 쿼리를 날린다")
    @Test
    public void dropTable() throws Exception {
        // given
        final Class<PersonV3> clazz = PersonV3.class;
        final Query query = queryBuilder.generateQuery(clazz);
        final JdbcTemplate jdbcTemplate = TestJdbcServerExtension.getJdbcTemplate();
        final String createQuery = queryBuilder.buildCreateQuery(query);
        jdbcTemplate.execute(createQuery);
        final String dropQuery = queryBuilder.buildDropQuery(query);

        // when
        jdbcTemplate.execute(dropQuery);

        // then
        List<String> tableNames = jdbcTemplate
                .query("SELECT table_name FROM information_schema.tables WHERE table_schema='PUBLIC'", resultSet -> resultSet.getString("table_name"));
        assertThat(tableNames).isEmpty();
    }

}