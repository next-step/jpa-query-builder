package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.domain.InsertPerson;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.dml.DmlQueryBuilder;

import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            DatabaseServer server = new H2();
            server.start();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            createTables(jdbcTemplate);

            DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
            InsertPerson insertPerson1 = new InsertPerson(1L, "test1", 20, "test@email.com", 1);
            jdbcTemplate.execute(dmlQueryBuilder.buildInsertQuery(insertPerson1));

            InsertPerson insertPerson2 = new InsertPerson(2L, "test2", 20, "test@email.com", 2);
            jdbcTemplate.execute(dmlQueryBuilder.buildInsertQuery(insertPerson2));

            List<InsertPerson> results = jdbcTemplate.query(
                    dmlQueryBuilder.buildSelectAllQuery(InsertPerson.class),
                    rs -> new InsertPerson(
                            rs.getLong("id"),
                            rs.getString("nick_name"),
                            rs.getInt("old"),
                            rs.getString("email"),
                            rs.getInt("id")
                    )
            );

            logger.info("Results에 insertPerson1 객체 포함: {}", results.contains(insertPerson1));
            logger.info("Results에 insertPerson2 객체 포함: {}", results.contains(insertPerson2));
            logger.info("Results size: {}", results.size());

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void createTables(JdbcTemplate jdbcTemplate) {
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        jdbcTemplate.execute(ddlQueryBuilder.buildCreateQuery(InsertPerson.class));
    }
}
