package persistence.sql.ddl;


import jdbc.JdbcTemplate;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.model.TableName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcServerTest
public class QueryBuilderIntegrationTest {

    private static final String SELECT_TABLES_SQL = "SELECT table_name FROM information_schema.tables WHERE table_schema='PUBLIC'";
    @Test
    void 테이블_생성() {
        Class<Person> clazz = Person.class;

        AbstractCreateQueryBuilder createQueryBuilder = new H2CreateQueryBuilder(clazz);
        JdbcTemplate jdbcTemplate = JdbcServerExtension.getJdbcTemplate();
        jdbcTemplate.execute(createQueryBuilder.makeQuery());

        List<String> tableNames = jdbcTemplate.query(
                SELECT_TABLES_SQL,
                resultSet -> resultSet.getString("table_name")
        );

        TableName tableName = new TableName(clazz);
        assertThat(tableNames).contains(tableName.getValue().toUpperCase());
    }

    @Test
    void 테이블_제거() {
        Class<Person> clazz = Person.class;

        AbstractCreateQueryBuilder createQueryBuilder = new H2CreateQueryBuilder(clazz);
        JdbcTemplate jdbcTemplate = JdbcServerExtension.getJdbcTemplate();
        jdbcTemplate.execute(createQueryBuilder.makeQuery());

        H2DropQueryBuilder dropQueryBuilder = new H2DropQueryBuilder(clazz);
        jdbcTemplate.execute(dropQueryBuilder.makeQuery());

        List<String> tableNames = jdbcTemplate.query(
                SELECT_TABLES_SQL,
                resultSet -> resultSet.getString("table_name")
        );

        assertThat(tableNames).isEmpty();
    }
}
