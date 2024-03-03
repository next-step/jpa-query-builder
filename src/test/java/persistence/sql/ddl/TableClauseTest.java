package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.annotated.Person;

import static persistence.sql.ddl.common.TestSqlConstant.DROP_TABLE;

class TableClauseTest {
    private static final Logger logger = LoggerFactory.getLogger(TableClauseTest.class);
    DatabaseServer server;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        try {
            server = new H2();
            server.start();
            jdbcTemplate = new JdbcTemplate(server.getConnection());
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(DROP_TABLE);
        server.stop();
    }

    @Test
    @DisplayName("[요구사항 2] @ColumnClause 애노테이션이 있는 Person 엔티티를 이용하여 create 쿼리 만든다.")
    void 요구사항2_test() {
        //given
        String expectedQuery = "CREATE TABLE IF NOT EXISTS Person " +
                "(id Long AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR(30) NULL,old INT NULL,email VARCHAR(30) NOT NULL)";

        // when
        String actualQuery = new CreateQueryBuilder(Person.class).getQuery();

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("[요구사항 3] 3.1 @Table 애노테이션이 붙은 필드의 name을 테이블명으로 가지는 create 쿼리 만든다.")
    void 요구사항3_1_test() {
        //given
        String expectedName = "users";

        // when
        String actualName = new TableClause(persistence.entity.notcolumn.Person.class).name();

        // then
        Assertions.assertThat(actualName).isEqualTo(expectedName);
    }
}