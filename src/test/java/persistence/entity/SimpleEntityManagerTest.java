package persistence.entity;

import database.DatabaseServer;
import database.H2;
import domain.Person3;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.dml.DmlQueryBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleEntityManagerTest {

    static DatabaseServer server;
    static JdbcTemplate jdbcTemplate;
    static SimpleEntityManager simpleEntityManager;
    static DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(new domain.step2.dialect.H2Dialect());
    static DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder(new domain.step3.dialect.H2Dialect());

    Person3 person;

    @BeforeAll
    static void init() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        simpleEntityManager = new SimpleEntityManager(jdbcTemplate, ddlQueryBuilder, dmlQueryBuilder);
    }

    @BeforeEach
    void setUp() {
        person = new Person3(1L, "test", 20, "test@test.com");
        createTable();
        insertData();
    }

    @AfterEach
    void remove() {
        dropTable();
    }

    @AfterAll
    static void destroy() {
        server.stop();
    }

    @DisplayName("entityManager find 테스트")
    @Test
    void findByIdTest() {
        Person3 person3 = simpleEntityManager.find(person.getClass(), person.getId());
        assertAll(
                () -> assertThat(person3.getId()).isEqualTo(person.getId()),
                () -> assertThat(person3.getEmail()).isEqualTo(person.getEmail()),
                () -> assertThat(person3.getName()).isEqualTo(person.getName()),
                () -> assertThat(person3.getAge()).isEqualTo(person.getAge())
        );
    }

    @DisplayName("entityManager persist 테스트")
    @Test
    void persistTest() {
        person = new Person3(2L, "test2", 30, "test2@test.com");
        simpleEntityManager.persist(person);

        Person3 person3 = simpleEntityManager.find(person.getClass(), 2L);
        assertAll(
                () -> assertThat(person3.getId()).isEqualTo(person.getId()),
                () -> assertThat(person3.getEmail()).isEqualTo(person.getEmail()),
                () -> assertThat(person3.getName()).isEqualTo(person.getName()),
                () -> assertThat(person3.getAge()).isEqualTo(person.getAge())
        );
    }

    @DisplayName("한 개의 데이터만 있을 때 삭제 후 findById 했을 경우 테스트")
    @Test
    void 하나의_데이터만_있을_경우_removeTest() {
        simpleEntityManager.remove(person);
        assertThrows(Exception.class, () -> simpleEntityManager.find(person.getClass(), 1L));
    }

    @DisplayName("여러 데이터가 있는 상황에서 그 데이터를 삭제 후 다른 id 값으로 findById 했을 경우 테스트")
    @Test
    void 추가_데이터_저장_후_removeTest() {
        //다른 person 데이터도 저장 후
        Person3 person2 = new Person3(2L, "test2", 30, "test2@test.com");
        simpleEntityManager.persist(person2);

        //2L 삭제
        simpleEntityManager.remove(person2);

        assertThrows(Exception.class, () -> simpleEntityManager.find(person.getClass(), 2L));

        Person3 person3 = simpleEntityManager.find(person.getClass(), 1L);
        assertAll(
                () -> assertThat(person3.getId()).isEqualTo(person.getId()),
                () -> assertThat(person3.getEmail()).isEqualTo(person.getEmail()),
                () -> assertThat(person3.getName()).isEqualTo(person.getName()),
                () -> assertThat(person3.getAge()).isEqualTo(person.getAge())
        );
    }

    private void createTable() {
        jdbcTemplate.execute(ddlQueryBuilder.createTable(Person3.class));
    }

    private void insertData() {
        jdbcTemplate.execute(dmlQueryBuilder.insertQuery(person));
    }

    private void dropTable() {
        jdbcTemplate.execute(ddlQueryBuilder.dropTable(Person3.class));
    }
}
