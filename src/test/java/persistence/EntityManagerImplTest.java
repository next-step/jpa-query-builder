package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.Person;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EntityManagerImplTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DatabaseServer server;
    EntityManager entityManager;

    @BeforeEach
    public void setUp() throws SQLException {
        server = new H2();
        server.start();

        entityManager = new EntityManagerImpl(new JdbcTemplate(server.getConnection()));
    }


    @Test
    @DisplayName("find 실행")
    public void findTest() {
        entityManager.createTable(Person.class);

        final Person person = new Person();
        person.setName("jinny");
        person.setAge(30);
        person.setEmail("test@gmail.com");

        entityManager.persist(person);

        Person persistedPerson = entityManager.find(Person.class, 1L);

        assertAll(
                () -> assertThat(persistedPerson).isNotNull(),
                () -> assertThat(persistedPerson.getName()).isEqualTo("jinny"),
                () -> assertThat(persistedPerson.getAge()).isEqualTo(30),
                () -> assertThat(persistedPerson.getEmail()).isEqualTo("test@gmail.com")
        );
    }

    @Test
    @DisplayName("delete 실행")
    public void removeTest() {
        entityManager.createTable(Person.class);

        final Person person = new Person();
        person.setName("jinny");
        person.setAge(30);
        person.setEmail("test@gmail.com");

        entityManager.persist(person);

        Person persistedPerson = entityManager.find(Person.class, 1L);

        entityManager.remove(persistedPerson);
    }
}
