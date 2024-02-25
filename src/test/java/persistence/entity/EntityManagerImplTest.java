package persistence.entity;

import database.sql.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EntityManagerImplTest {
    @Test
    void find() {
        Person person = new Person("abc123", 14, "c123@d.com");
        List<String> executedQueries = new ArrayList<>();

        JdbcTemplateMock jdbcTemplateMock = new JdbcTemplateMock(person, executedQueries);
        EntityManager entityManager = new EntityManagerImpl(jdbcTemplateMock);

        Person found = entityManager.find(Person.class, 1L);

        assertThat(executedQueries).containsExactly("SELECT id, nick_name, old, email FROM users WHERE id = 1");
        assertThat(found).isEqualTo(person);
    }

    @Test
    void persist() {
        Person person = new Person("abc123", 14, "c123@d.com");
        List<String> executedQueries = new ArrayList<>();

        JdbcTemplateMock jdbcTemplateMock = new JdbcTemplateMock(null, executedQueries);
        EntityManager entityManager = new EntityManagerImpl(jdbcTemplateMock);

        entityManager.persist(person);

        assertThat(executedQueries)
                .containsExactly("INSERT INTO users (nick_name, old, email) VALUES ('abc123', 14, 'c123@d.com')");
    }

    @Test
    void remove() {
        PersonWithId entity = new PersonWithId(1010L);
        List<String> executedQueries = new ArrayList<>();

        JdbcTemplateMock jdbcTemplateMock = new JdbcTemplateMock(null, executedQueries);
        EntityManager entityManager = new EntityManagerImpl(jdbcTemplateMock);
        entityManager.remove(entity);

        assertThat(executedQueries)
                .containsExactly("DELETE FROM person_with_id WHERE id = 1010");
    }

    private static class JdbcTemplateMock extends JdbcTemplate {
        private final Person person;
        private final List<String> executedQueries;

        public JdbcTemplateMock(Person person, List<String> executedQueries) {
            super(null);
            this.person = person;
            this.executedQueries = executedQueries;
        }

        @Override
        public void execute(String sql) {
            executedQueries.add(sql);
        }

        @Override
        public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
            executedQueries.add(sql);
            return (T) this.person;
        }
    }

    @Entity
    @Table(name = "person_with_id")
    private static class PersonWithId {
        @Id
        private final Long id;

        public PersonWithId(long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }
}
