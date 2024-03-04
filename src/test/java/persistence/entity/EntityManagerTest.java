package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.notcolumn.Person;
import persistence.sql.common.DtoMapper;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import java.util.List;
import java.util.stream.Stream;

import static persistence.entity.TestFixture.*;
import static persistence.entity.TestFixture.person_철수;
import static persistence.sql.ddl.common.TestSqlConstant.DROP_TABLE_USERS;

class EntityManagerTest {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerTest.class);
    DatabaseServer server;
    private JdbcTemplate jdbcTemplate;

    EntityManager<Person> entityManager;

    @BeforeEach
    void setUp() {
        try {
            server = new H2();
            server.start();
            jdbcTemplate = new JdbcTemplate(server.getConnection());
            entityManager = new EntityManagerImpl<>(jdbcTemplate);

            jdbcTemplate.execute(new CreateQueryBuilder(Person.class).getQuery());
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(DROP_TABLE_USERS);
        server.stop();
    }

    @Test
    void find() {
        // given
        jdbcTemplate.execute(new InsertQueryBuilder(Person.class).getInsertQuery(person_철수));

        // when
        Person actual = entityManager.find(Person.class, 1L);

        // then
        Assertions.assertThat(actual).isEqualTo(person_철수_저장결과);
    }

    @Test
    void persist() {
        // given & when
        Person actual = (Person) entityManager.persist(person_철수);

        // then
        Assertions.assertThat(actual).isEqualTo(person_철수_저장결과);
    }

    @Test
    void remove() {
        // given
        List<String> insertQueries = Stream.of(persistence.sql.dml.TestFixture.person_철수, person_영희, person_짱구)
                .map(person -> new InsertQueryBuilder(Person.class).getInsertQuery(person)).toList();

        for (String query : insertQueries) {
            jdbcTemplate.execute(query);
        }

        // when
        entityManager.remove(person_철수_저장결과);

        // then
        String query = new SelectQueryBuilder(Person.class).getFindAllQuery();
        List<Person> actual = jdbcTemplate.query(query, new DtoMapper<>(Person.class));
        Assertions.assertThat(actual).containsExactlyInAnyOrder(TestFixture.person_영희_저장결과, TestFixture.person_짱구_저장결과);
    }
}