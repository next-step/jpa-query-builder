package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.domain.Person;
import persistence.sql.ddl.DdlQueryBuilder;

import java.sql.SQLException;
import java.util.List;

public abstract class DatabaseTest {

    private static final Logger log = LoggerFactory.getLogger(DatabaseTest.class);

    protected DatabaseServer database;

    @BeforeEach
    protected void setUp() throws Exception {
        database = new H2();
        database.start();
        clearTables();
    }

    @AfterEach
    protected void tearDown() {
        database.stop();
    }

    private void clearTables() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database.getConnection());

        getTableNames().forEach(tableName -> dropTable(jdbcTemplate, tableName));
    }

    private void dropTable(JdbcTemplate jdbcTemplate, String tableName) {
        String fetchTablesSql = """
                 SELECT TABLE_NAME\s
                 FROM INFORMATION_SCHEMA.TABLES\s
                 WHERE TABLE_NAME = '%s'\s
                \s""".formatted(tableName.toUpperCase());

        jdbcTemplate.query(fetchTablesSql, rs -> rs.getString("TABLE_NAME"))
                .forEach(name -> {
                            jdbcTemplate.execute("DROP TABLE " + name);
                            log.info("Dropped table: {}", name);
                        }
                );
    }

    protected abstract List<String> getTableNames();

    public void createTable(Class<Person> clazz) throws Exception {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database.getConnection());
        jdbcTemplate.execute(ddlQueryBuilder.buildCreateQuery(clazz));
    }
}
