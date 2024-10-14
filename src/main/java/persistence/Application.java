package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            final String sql = """
                    CREATE TABLE PERSON (
                        id BIGINT PRIMARY KEY,
                        name VARCHAR(255),
                        age INTEGER
                    );
                    """;
            jdbcTemplate.execute(sql);

            final List<String> tableNames = jdbcTemplate.query(
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'PERSON'",
                    resultSet -> resultSet.getString("TABLE_NAME")
            );

            final boolean isTableCreated = !tableNames.isEmpty();

            if (isTableCreated) {
                logger.info("Person 테이블이 성공적으로 생성되었습니다.");
            } else {
                logger.error("Person 테이블 생성에 실패했습니다.");
                throw new IllegalStateException("Person 테이블 생성에 실패했습니다.");
            }

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
