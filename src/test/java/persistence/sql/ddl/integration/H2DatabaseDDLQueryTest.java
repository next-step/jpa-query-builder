package persistence.sql.ddl.integration;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.dialect.H2Dialect;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class H2DatabaseDDLQueryTest {

    private DatabaseServer server;

    @BeforeEach
    void beforeEach() throws SQLException {
        server = new H2();
        server.start();
    }

    @AfterEach
    void afterEach() {
        server.stop();
    }

    @Test
    @DisplayName("[성공] table create")
    void createTable() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
        CreateQueryBuilder queryBuilder = new CreateQueryBuilder();

        jdbcTemplate.execute(queryBuilder.build(Person.class, new H2Dialect()));

        Integer count = jdbcTemplate.queryForObject(
                "select count(1) from information_schema.tables"
                        + " where table_name = 'USERS'"
                        + " and table_schema = 'PUBLIC'",
                (rs) -> rs.getInt(1)
        );
        assertEquals(count, 1);
    }

    @Test
    @DisplayName("[성공] drop table")
    void dropTable() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder();
        jdbcTemplate.execute(createQueryBuilder.build(Person.class, new H2Dialect()));
        Integer tableCountAfterCreate = jdbcTemplate.queryForObject(
                "select count(1) from information_schema.tables"
                        + " where table_name = 'USERS'"
                        + " and table_schema = 'PUBLIC'",
                (rs) -> rs.getInt(1)
        );

        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder();
        jdbcTemplate.execute(dropQueryBuilder.build(Person.class, new H2Dialect()));
        Integer tableCountAfterDrop = jdbcTemplate.queryForObject(
                "select count(1) from information_schema.tables"
                        + " where table_name = 'USERS'"
                        + " and table_schema = 'PUBLIC'",
                (rs) -> rs.getInt(1)
        );

        assertAll("테이블 생성 후 삭제 검증",
                () -> assertEquals(tableCountAfterCreate, 1),
                () -> assertEquals(tableCountAfterDrop, 0)
        );
    }
}
