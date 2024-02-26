package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.assertj.core.api.Assertions;

import static persistence.sql.ddl.common.TestSqlConstant.DROP_TABLE;

class CreateQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(CreateQueryBuilderTest.class);
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
    @DisplayName("[요구사항 1] @Column 애노테이션이 없는 Person 엔티티를 이용하여 create 쿼리 만든다.")
    void 요구사항1_test() {
        //given
        String expectedQuery = "CREATE TABLE IF NOT EXISTS Person " +
                "(id Long AUTO_INCREMENT PRIMARY KEY,name VARCHAR(30) NULL,age INT NULL)";
        // when
        String actualQuery = new CreateQueryBuilder(persistence.entity.basic.Person.class).getCreateQuery();

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("[요구사항 3.2] @Transient 애노테이션이 붙은 필드는 제하고 컬럼 만든다.")
    void 요구사항3_2_test() {
        //given
        List<String> expectedColumnQueries = List.of("nick_name VARCHAR(30) NULL",
                "old INT NULL",
                "email VARCHAR(30) NOT NULL");

        // when
        List<String> actualColumnQueries = new Table(persistence.entity.notcolumn.Person.class).getColumnQueries();

        // then
        Assertions.assertThat(expectedColumnQueries.containsAll(actualColumnQueries)).isTrue();
        Assertions.assertThat(actualColumnQueries).hasSize(3);
    }

    @Test
    @DisplayName("[요구사항 4] drop table 쿼리를 만들어라")
    void 요구사항3_3_test() {
        //given
        String expectedQuery = "DROP TABLE IF EXISTS users";

        // when
        String actualQuery = new CreateQueryBuilder(persistence.entity.notcolumn.Person.class).getDropTableQuery();

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }
}