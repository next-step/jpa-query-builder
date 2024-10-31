package persistence.entity;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.Test;
import persistence.DatabaseTest;
import persistence.domain.Person;
import persistence.sql.dml.DmlQueryBuilder;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class EntityManagerImplTest extends DatabaseTest {

    @Override
    public List<String> getTableNames() {
        return List.of("my_users");
    }

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
        DmlQueryBuilder<Person> dmlQueryBuilder = DmlQueryBuilder.from(Person.class);
        jdbcTemplate.execute(dmlQueryBuilder.buildInsertQuery(person));
    }
}
