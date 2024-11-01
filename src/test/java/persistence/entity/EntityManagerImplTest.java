package persistence.entity;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.DatabaseTest;
import persistence.domain.Person;
import persistence.sql.dml.DmlQueryBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class EntityManagerImplTest extends DatabaseTest {

    @Override
    public List<String> getTableNames() {
        return List.of("my_users");
    }

    @DisplayName("데이터베이스에서 객체를 조회한다")
    @Test
    void find() throws Exception {
        createTable(Person.class);
        insertData(new Person("bob", 32, "test@email.com"));
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database.getConnection());

        EntityManager<Person> entityManager = new EntityManagerImpl<>(jdbcTemplate);
        Person person = entityManager.find(Person.class, 1L);

        assertSoftly(softly -> {
            softly.assertThat(person.getId()).isEqualTo(1L);
            softly.assertThat(person.getName()).isEqualTo("bob");
            softly.assertThat(person.getAge()).isEqualTo(32);
            softly.assertThat(person.getEmail()).isEqualTo("test@email.com");
        });
    }

    private void insertData(Person person) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database.getConnection());
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        jdbcTemplate.execute(dmlQueryBuilder.buildInsertQuery(person));
    }

    @DisplayName("객체를 데이터베이스에 저장한다")
    @Test
    void persist() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database.getConnection());
        EntityManager<Person> entityManager = new EntityManagerImpl<>(jdbcTemplate);

        createTable(Person.class);
        entityManager.persist(new Person("bob", 32, "test@email.com"));

        Person savedPerson = jdbcTemplate.queryForObject("select * from my_users", new DefaultRowMapper<>(Person.class));

        assertSoftly(softly -> {
            softly.assertThat(savedPerson.getId()).isEqualTo(1L);
            softly.assertThat(savedPerson.getName()).isEqualTo("bob");
            softly.assertThat(savedPerson.getAge()).isEqualTo(32);
            softly.assertThat(savedPerson.getEmail()).isEqualTo("test@email.com");
        });
    }

    @DisplayName("객체를 데이터베이스에서 삭제한다")
    @Test
    void remove() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database.getConnection());
        EntityManager<Person> entityManager = new EntityManagerImpl<>(jdbcTemplate);

        createTable(Person.class);
        insertData(new Person("bob", 32, "test@email.com"));

        Person person = new Person(1L, "bob", 32, "test@email.com", 1);
        entityManager.remove(person);

        List<Person> users = jdbcTemplate.query("select * from my_users", new DefaultRowMapper<>(Person.class));

        assertThat(users).isEmpty();
    }
}
