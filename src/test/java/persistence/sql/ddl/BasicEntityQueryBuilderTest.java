package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.h2.jdbc.JdbcResultSet;
import org.h2.result.ResultInterface;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.assertj.core.api.Assertions;
import persistence.sql.entity.annotatedentity.Person;

class BasicEntityQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(BasicEntityQueryBuilderTest.class);
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
        server.stop();
    }

    @Test
    @DisplayName("[요구사항1] Person 클래스를 사용하여, person 테이블을 생성한다.")
    void createQueryUsingEntityTest() throws SQLException {
        //given
        List<String> expectedColumnNames = List.of("ID", "NAME", "AGE");
        String expectedTableName = "PERSON";

        // when
        jdbcTemplate.execute(new BasicEntityQueryBuilder(persistence.sql.entity.baic.Person.class).build());

        // then
        ResultSet selectQueryResult = server.getConnection().createStatement().executeQuery("SELECT * FROM person");
        ResultInterface tableSchema = ((JdbcResultSet) selectQueryResult).getResult();

        String actualTableName = tableSchema.getTableName(0);
        String columnName1 = tableSchema.getColumnName(0);
        String columnName2 = tableSchema.getColumnName(1);
        String columnName3 = tableSchema.getColumnName(2);
        List<String> actualColumnNames = List.of(columnName1, columnName2, columnName3);

        Assertions.assertThat(actualTableName).isEqualTo(expectedTableName);
        Assertions.assertThat(actualColumnNames.containsAll(expectedColumnNames)).isTrue();
    }

    @Test
    @DisplayName("[요구사항2] Person 클래스를 사용하여, person 테이블을 생성한다.")
    void createQueryUsingAnnotatedEntityTest() throws SQLException {
        //given
        List<String> expectedColumnNames = List.of("ID", "NICK_NAME", "OLD", "EMAIL");
        String expectedTableName = "PERSON";

        // when
        jdbcTemplate.execute(new AnnotatedEntityQueryBuilder(Person.class).build());

        // then
        ResultSet selectQueryResult = server.getConnection().createStatement().executeQuery("SELECT * FROM person");
        ResultInterface tableSchema = ((JdbcResultSet) selectQueryResult).getResult();

        String actualTableName = tableSchema.getTableName(0);
        String columnName1 = tableSchema.getColumnName(0);
        String columnName2 = tableSchema.getColumnName(1);
        String columnName3 = tableSchema.getColumnName(2);
        String columnName4 = tableSchema.getColumnName(3);
        List<String> actualColumnNames = List.of(columnName1, columnName2, columnName3, columnName4);

        Assertions.assertThat(actualTableName).isEqualTo(expectedTableName);
        Assertions.assertThat(actualColumnNames.containsAll(expectedColumnNames)).isTrue();
    }
}