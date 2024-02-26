package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import jdbc.DtoMapper;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.notcolumn.Person;
import persistence.sql.ddl.CreateQueryBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static persistence.sql.ddl.common.TestSqlConstant.DROP_TABLE;
import static persistence.sql.dml.TestFixture.*;

class DeleteQueryBuilderTest {
    private static final Logger logger = LoggerFactory.getLogger(DeleteQueryBuilderTest.class);
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

    @DisplayName("[요구사항4.1] 3건의 person insert 후, delteAll을 실행시, 0건이 조회된다.")
    @Test
    void 요구사항2_test() throws SQLException {
        // given
        insertTestFixtures();
        Long randomPersonId = getRandomPersonId();

        // when
        String query = new DeleteQueryBuilder(Person.class).deleteAll();
        jdbcTemplate.execute(query);

        // then
        String findAllQuery = new SelectQueryBuilder(Person.class).getFindAllQuery();
        List<Person> persons = jdbcTemplate.query(findAllQuery, new DtoMapper<Person>(Person.class));

        Assertions.assertThat(persons).isEmpty();
    }

    @DisplayName("[요구사항4.2] 3건의 person insert 후, deleteById 실행시, 2건이 조회된다.")
    @Test
    void 요구사항3_test() {
        // given
        insertTestFixtures();
        Long randomPersonId = getRandomPersonId();

        // when
        String query = new DeleteQueryBuilder(Person.class).deleteById(randomPersonId);
        jdbcTemplate.execute(query);

        String selectQuery = new SelectQueryBuilder(Person.class).getFindAllQuery();
        List<Person> persons = jdbcTemplate.query(selectQuery, new DtoMapper<Person>(Person.class));

        // then
        Assertions.assertThat(persons).hasSize(2);
    }

    private Long getRandomPersonId() {
        String selectAllQuery = new SelectQueryBuilder(Person.class).getFindAllQuery();
        List<Person> persons = jdbcTemplate.query(selectAllQuery, new DtoMapper<Person>(Person.class));
        return persons.get(0).getId();
    }

    private void insertTestFixtures() {
        jdbcTemplate.execute(new CreateQueryBuilder(Person.class).getQuery());

        List<String> insertQueries = Stream.of(person_철수, person_영희, person_짱구)
                .map(person -> new InsertQueryBuilder(Person.class).getInsertQuery(person)).toList();

        for (String query : insertQueries) {
            jdbcTemplate.execute(query);
        }
    }
}