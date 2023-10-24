package persistence.entity;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import fixture.PersonFixtureFactory;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DdlQueryGenerator;
import persistence.sql.dialect.DialectFactory;
import persistence.sql.meta.EntityMeta;
import persistence.sql.meta.MetaFactory;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcEntityManagerTest {

    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;
    private DdlQueryGenerator ddlQueryGenerator;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());

        EntityMeta personMeta = MetaFactory.get(Person.class);
        DialectFactory dialectFactory = DialectFactory.getInstance();
        ddlQueryGenerator = DdlQueryGenerator.of(dialectFactory.getDialect(jdbcTemplate.getDbmsName()));
        jdbcTemplate.execute(ddlQueryGenerator.generateCreateQuery(personMeta));
    }

    @AfterEach
    void tearDown() {
        EntityMeta personMeta = MetaFactory.get(Person.class);
        jdbcTemplate.execute(ddlQueryGenerator.generateDropQuery(personMeta));
        server.stop();
    }

    @Test
    @DisplayName("엔티티 조회")
    void find() {
        JdbcEntityManager jdbcEntityManager = JdbcEntityManager.of(jdbcTemplate);
        PersonFixtureFactory.getFixtures()
                .forEach(jdbcEntityManager::persist);

        Person person = jdbcEntityManager.find(Person.class, 1L);
        assertThat(person).isNotNull();
    }

    @Test
    @DisplayName("엔티티 생성 테스트")
    void persist() {
        JdbcEntityManager jdbcEntityManager = JdbcEntityManager.of(jdbcTemplate);
        jdbcEntityManager.persist(new Person("테스트", 20, "test@domain.com", 1));

        Person person = jdbcEntityManager.find(Person.class, 1L);
        assertThat(person).isNotNull();
    }

    @Test
    @DisplayName("엔티티 삭제 테스트")
    void remove() {
        JdbcEntityManager jdbcEntityManager = JdbcEntityManager.of(jdbcTemplate);
        PersonFixtureFactory.getFixtures()
                .forEach(jdbcEntityManager::persist);

        Person person = jdbcEntityManager.find(Person.class, 1L);
        jdbcEntityManager.remove(person);

        person = jdbcEntityManager.find(Person.class, 1L);
        assertThat(person).isNull();
    }
}
