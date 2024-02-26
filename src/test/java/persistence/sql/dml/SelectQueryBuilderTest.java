package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import jdbc.DtoMapper;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.notcolumn.Person;
import persistence.sql.ddl.CreateQueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static persistence.sql.ddl.common.TestSqlConstant.DROP_TABLE;
import static persistence.sql.dml.TestFixture.*;

class SelectQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(SelectQueryBuilderTest.class);
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

    @DisplayName("[요구사항2] 3건의 person insert 후, findAll을 실행시, 3건이 조회된다.")
    @Test
    void 요구사항2_test() throws SQLException {
        // given
        jdbcTemplate.execute(new CreateQueryBuilder(Person.class).getQuery());

        List<String> insertQueries = Stream.of(person_철수, person_영희, person_짱구)
                .map(person -> new InsertQueryBuilder(Person.class).getInsertQuery(person)).toList();

        for (String query : insertQueries) {
            jdbcTemplate.execute(query);
        }

        // when
        String findAllQuery = new SelectQueryBuilder(Person.class).getFindAllQuery();
        List<Person> persons = jdbcTemplate.query(findAllQuery, new DtoMapper<Person>(Person.class));


        // then
        Assertions.assertThat(persons).hasSize(3);
    }

    @DisplayName("[요구사항3] 3건의 person insert 후, findById를 실행시, 1건이 조회된다.")
    @Test
    void 요구사항3_test() {
        // given
        jdbcTemplate.execute(new CreateQueryBuilder(Person.class).getQuery());

        List<String> insertQueries = Stream.of(person_철수, person_영희, person_짱구)
                .map(person -> new InsertQueryBuilder(Person.class).getInsertQuery(person)).toList();

        for (String query : insertQueries) {
            jdbcTemplate.execute(query);
        }

        // when
        String query = new SelectQueryBuilder(Person.class).getFindById(1L);
        jdbcTemplate.query(query, new DtoMapper<Person>(Person.class));
        Person selectedPerson = (Person) jdbcTemplate.queryForObject(query, new DtoMapper<Person>(Person.class));

        // then
        Assertions.assertThat(selectedPerson.getId()).isEqualTo(1L);
    }

}