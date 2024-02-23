package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.domain.Dialect;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MyEntityManagerTest {

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseServer server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Dialect.H2);
        String createQuery = createQueryBuilder.build(Person.class);
        jdbcTemplate.execute(createQuery);
    }

    @Test
    @DisplayName("find 메서드는 주어진 클래스와 id에 해당하는 엔티티를 반환한다")
    void find() {
        // given
        MyEntityManager entityManager = new MyEntityManager(jdbcTemplate);
        Long id = 1L;
        Person expected = new Person(id, "John", 25, "qwer@asdf.com", 1);
        String insertQuery = new InsertQueryBuilder().build(expected);
        jdbcTemplate.execute(insertQuery);

        // when
        Person person = entityManager.find(Person.class, id);

        // then
        assertThat(person).isNotNull();
    }

    @Test
    @DisplayName("persist 메서드는 주어진 객체를 저장한다.")
    void persist() {
        // given
        MyEntityManager entityManager = new MyEntityManager(jdbcTemplate);
        Long id = 1L;
        String expectedName = "John";
        Person expected = new Person(id, expectedName, 25, "qwer@asdf.com", 1);
        entityManager.persist(expected);

        // when
        Person person = entityManager.find(Person.class, id);

        // then
        assertThat(person).extracting("name")
                .isEqualTo(expectedName);
    }

    @Test
    @DisplayName("remove 메서드는 주어진 객체를 삭제한다.")
    void remove() {
        //given
        MyEntityManager entityManager = new MyEntityManager(jdbcTemplate);
        Person expected = new Person(1L, "name", 25, "qwer@asdf.com", 1);
        String insertQuery = new InsertQueryBuilder().build(expected);
        jdbcTemplate.execute(insertQuery);

        //when
        entityManager.remove(expected);

        //then
        assertThatThrownBy(() -> entityManager.find(Person.class, 1L))
                .isInstanceOf(RuntimeException.class);
    }
}
