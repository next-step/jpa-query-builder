package persistence.sql.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import database.DatabaseServer;
import database.H2;
import java.sql.SQLException;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.ddl.query.CreateQuery;
import persistence.sql.ddl.query.DropQuery;
import persistence.sql.ddl.query.builder.CreateQueryBuilder;
import persistence.sql.ddl.query.builder.DropQueryBuilder;
import persistence.sql.dialect.H2Dialect;

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

        CreateQuery query = new CreateQuery(Person.class);
        CreateQueryBuilder queryBuilder = CreateQueryBuilder.builder(new H2Dialect())
                .create(query.tableName(), query.identifier(), query.columns());

        jdbcTemplate.execute(queryBuilder.build());

        Integer count = jdbcTemplate.queryForObject(
                getTableExistCheckSelectQuery(),
                (rs) -> rs.getInt(1)
        );
        assertEquals(count, 1);
    }

    @Test
    @DisplayName("[성공] drop table")
    void dropTable() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

        CreateQuery createQuery = new CreateQuery(Person.class);
        CreateQueryBuilder queryBuilder = CreateQueryBuilder.builder(new H2Dialect())
                .create(createQuery.tableName(), createQuery.identifier(), createQuery.columns());

        jdbcTemplate.execute(queryBuilder.build());
        Integer tableCountAfterCreate = jdbcTemplate.queryForObject(
                getTableExistCheckSelectQuery(),
                (rs) -> rs.getInt(1)
        );

        DropQuery dropQuery = new DropQuery(Person.class);
        DropQueryBuilder dropQueryBuilder = DropQueryBuilder.builder(new H2Dialect())
                .drop(dropQuery.tableName());

        jdbcTemplate.execute(dropQueryBuilder.build());
        Integer tableCountAfterDrop = jdbcTemplate.queryForObject(
                getTableExistCheckSelectQuery(),
                (rs) -> rs.getInt(1)
        );

        assertAll("테이블 생성 후 삭제 검증",
                () -> assertEquals(tableCountAfterCreate, 1),
                () -> assertEquals(tableCountAfterDrop, 0)
        );
    }

    private String getTableExistCheckSelectQuery() {
        return """
                select count(1) from information_schema.tables where table_name = 'USERS' and table_schema = 'PUBLIC'""";
    }
}
