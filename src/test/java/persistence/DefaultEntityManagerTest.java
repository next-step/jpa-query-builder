package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DDLGenerator;
import persistence.sql.dml.DMLGenerator;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DefaultEntityManagerTest {

    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;
    private EntityManager entityManager;

    DDLGenerator ddlGenerator = new DDLGenerator(Person.class);
    DMLGenerator dmlGenerator = new DMLGenerator(Person.class);

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        jdbcTemplate.execute(ddlGenerator.generateCreate());

        entityManager = new DefaultEntityManager(jdbcTemplate, dmlGenerator);
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    @DisplayName("Person 을 조회한다.")
    void find() {
        // given
        long id = 1L;
        jdbcTemplate.execute(dmlGenerator.generateInsert(new Person(id, "name", 26, "email")));

        // when
        Person person = entityManager.find(Person.class, id);

        // then
        assertThat(person.getId()).isEqualTo(id);
    }

}