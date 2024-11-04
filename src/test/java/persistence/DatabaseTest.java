package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.dml.DmlQueryBuilder;

import java.util.List;

public abstract class DatabaseTest {

    private static final Logger log = LoggerFactory.getLogger(DatabaseTest.class);

    protected DatabaseServer database;
    protected JdbcTemplate jdbcTemplate;
    protected DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
    protected DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();

    @BeforeEach
    protected void setUp() throws Exception {
        database = new H2();
        database.start();
        jdbcTemplate = new JdbcTemplate(database.getConnection());
        clearTables();
    }

    @AfterEach
    protected void tearDown() {
        database.stop();
    }

    private void clearTables() {
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

    protected void createTable(Class<?> clazz) {
        jdbcTemplate.execute(ddlQueryBuilder.buildCreateQuery(clazz));
    }

    protected void insert(Object entity) {
        jdbcTemplate.execute(dmlQueryBuilder.buildInsertQuery(entity));
    }

    protected void dropTable(Class<?> clazz) {
        jdbcTemplate.execute(ddlQueryBuilder.buildDropQuery(clazz));
    }

    protected void delete(Object entity) {
        jdbcTemplate.execute(dmlQueryBuilder.buildDeleteQuery(entity));
    }
}
