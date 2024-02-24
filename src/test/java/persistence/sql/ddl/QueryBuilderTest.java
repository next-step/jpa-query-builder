package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.h2.jdbc.JdbcResultSet;
import org.h2.jdbc.JdbcSQLSyntaxErrorException;
import org.h2.result.ResultInterface;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import persistence.entity.annotated.Person;

import static java.sql.ResultSetMetaData.columnNullable;
import static persistence.sql.ddl.common.TestSqlConstant.DROP_TABLE;
import static persistence.sql.ddl.common.TestSqlConstant.SELECT_ALL_ROWS;

class QueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(QueryBuilderTest.class);
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
    void 요구사항1_test() throws SQLException {
        //given
        String expectedQuery = "CREATE TABLE IF NOT EXISTS Person " +
                "(id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(30) NULL,age INT)";
        // when
        String actualQuery = new QueryBuilder(persistence.entity.basic.Person.class).getCreateQuery();

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("[요구사항 2] @Column 애노테이션이 있는 Person 엔티티를 이용하여 create 쿼리 만든다.")
    void 요구사항2_test() throws SQLException {
        //given
        String expectedQuery = "CREATE TABLE IF NOT EXISTS Person " +
                "(id INT AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR(30) NULL,old INT,email VARCHAR(30) NOT NULL)";

        // when
        String actualQuery = new QueryBuilder(Person.class).getCreateQueryUsingAnnotation();

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("[요구사항 3] 3.1 @Table 애노테이션이 붙은 필드의 name을 테이블명으로 가지는 create 쿼리 만들")
    void 요구사항3_1_test() throws SQLException {
        //given
        String expectedQuery = "CREATE TABLE IF NOT EXISTS users " +
                "(id INT AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR(30) NULL,old INT,email VARCHAR(30) NOT NULL)";

        // when
        String actualQuery = new QueryBuilder(persistence.entity.notcolumn.Person.class).getCreateQueryUsingAnnotation();

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("[요구사항 3.2] @Transient 애노테이션이 붙은 필드는 제하고 create 쿼리 만든다.")
    void 요구사항3_2_test() throws SQLException {
        //given
        String expectedQuery = "CREATE TABLE IF NOT EXISTS users " +
                "(id INT AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR(30) NULL,old INT,email VARCHAR(30) NOT NULL)";

        // when
        String actualQuery = new QueryBuilder(persistence.entity.notcolumn.Person.class).getCreateQueryUsingAnnotation();

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("[요구사항 4] drop table 쿼리를 만들어라")
    void 요구사항3_3_test() throws SQLException {
        //given
        String expectedQuery = "DROP TABLE IF EXISTS users";

        // when
        String actualQuery = new QueryBuilder(persistence.entity.notcolumn.Person.class).getDropTableQuery();

        // then
        Assertions.assertThat(actualQuery).isEqualTo(expectedQuery);
    }
}