package persistence.sql.dml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import database.DatabaseServer;
import database.H2;
import java.util.List;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.domain.Person;
import persistence.sql.ddl.PersistentEntity;

class JpaRepositoryImplTest {

    private static final Logger logger = LoggerFactory.getLogger(JpaRepositoryImplTest.class);
    private static JdbcTemplate jdbcTemplate;
    private static DatabaseServer server;

    @BeforeAll
    public static void setUp() {
        try {
            server = new H2();
            server.start();

            jdbcTemplate = new JdbcTemplate(server.getConnection());
        } catch (Exception e) {
            logger.error("Error occurred", e);
        }
    }

    @BeforeEach
    void createTable() {
        PersistentEntity entity = new PersistentEntity(jdbcTemplate);
        entity.createTable(Person.class);
    }

    @Test
    @DisplayName("insert && findById 구현해보기")
    void saveTest() throws IllegalAccessException {
        JpaRepository<Person, Long> jpaRepository = new JpaRepositoryImpl<>(Person.class,
            jdbcTemplate);
        Person person = Person.builder()
            .name("John")
            .age(20)
            .email("John@naver.com")
            .build();
        jpaRepository.save(person);

        Person personOne = jpaRepository.findById(1L);

        assertThat(personOne.getId()).isNotNull();
        assertThat(personOne.getName()).isEqualTo("John");
        assertThat(personOne.getAge()).isEqualTo(20);
        assertThat(personOne.getEmail()).isEqualTo("John@naver.com");
    }

    @Test
    @DisplayName("findByAll 구현해보기")
    void findAllTest() throws IllegalAccessException {
        JpaRepository<Person, Long> jpaRepository = new JpaRepositoryImpl<>(Person.class,
            jdbcTemplate);
        Person person1 = Person.builder()
            .name("John1")
            .age(20)
            .email("John1@naver.com")
            .build();

        Person person2 = Person.builder()
            .name("John2")
            .age(20)
            .email("John2@naver.com")
            .build();

        Person person3 = Person.builder()
            .name("John3")
            .age(20)
            .email("John3@naver.com")
            .build();

        jpaRepository.save(person1);
        jpaRepository.save(person2);
        jpaRepository.save(person3);

        List<Person> personList = jpaRepository.findAll();
        assertThat(personList.size()).isEqualTo(3);

    }


    @Test
    @DisplayName("delete 구현해보기")
    void deleteTest() throws IllegalAccessException {
        JpaRepository<Person, Long> jpaRepository = new JpaRepositoryImpl<>(Person.class,
            jdbcTemplate);
        Person person = Person.builder()
            .name("John")
            .age(20)
            .email("john@naver.com")
            .build();

        jpaRepository.save(person);
        Person personOne = jpaRepository.findById(1L);
        jpaRepository.delete(personOne);

        assertThatThrownBy(() -> jpaRepository.findById(1L)).isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Expected 1 result, got 0");
    }

    @Test
    @DisplayName("deleteById 구현해보기")
    void deleteByIdTest() throws IllegalAccessException {
        JpaRepository<Person, Long> jpaRepository = new JpaRepositoryImpl<>(Person.class,
            jdbcTemplate);
        Person person = Person.builder()
            .name("John")
            .email("john@naver.com")
            .build();

        jpaRepository.save(person);
        jpaRepository.deleteById(1L);

        assertThatThrownBy(() -> jpaRepository.findById(1L)).isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Expected 1 result, got 0");
    }

    @Test
    @DisplayName("deleteAll 구현해보기")
    void deleteAllTest() throws IllegalAccessException {
        JpaRepository<Person, Long> jpaRepository = new JpaRepositoryImpl<>(Person.class,
            jdbcTemplate);
        Person person1 = Person.builder()
            .name("John1")
            .email("john1@naver.com")
            .build();

        Person person2 = Person.builder()
            .name("John2")
            .email("john2@naver.com")
            .build();

        Person person3 = Person.builder()
            .name("John3")
            .email("john3@naver.com")
            .build();

        jpaRepository.save(person1);
        jpaRepository.save(person2);
        jpaRepository.save(person3);

        jpaRepository.deleteAll();

        List<Person> personList = jpaRepository.findAll();
        assertThat(personList.size()).isEqualTo(0);
    }

    @AfterEach
    void dropTable() {
        PersistentEntity entity = new PersistentEntity(jdbcTemplate);
        entity.dropTable(Person.class);
    }

    @AfterAll
    public static void afterAllTests() {
        try {
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        }
    }

}
