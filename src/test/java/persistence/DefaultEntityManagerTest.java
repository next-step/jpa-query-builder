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
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

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
    void find_1() {
        // given
        long id = 1L;
        jdbcTemplate.execute(dmlGenerator.generateInsert(new Person(id, "name", 26, "email")));

        // when
        Person person = entityManager.find(Person.class, id);

        // then
        assertThat(person.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("존재하지 않는 id로 조회할 경우 null을 반환한다.")
    void find_2() {
        // given
        long id = 1L;

        // when
        Person person = entityManager.find(Person.class, id);

        // then
        assertThat(person).isNull();
    }

    @Test
    @DisplayName("id가 null 일 경우 예외가 발생한다.")
    void find_3() {
        // given
        Long id = null;

        // when
        Throwable throwable = catchThrowable(() -> entityManager.find(Person.class, id));

        // then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[EntityManager] find: id is null");
    }
}