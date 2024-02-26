package persistence.sql.entity.manager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.db.H2Database;
import persistence.sql.dml.repository.Repository;
import persistence.sql.dml.repository.RepositoryImpl;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMangerTest extends H2Database {

    private EntityManger<Person> entityManger;
    private Repository<Person> personRepository;

    private Person person;

    @BeforeEach
    void setUp() {
        this.entityManger = new EntityManagerImpl<>(jdbcTemplate);
        this.personRepository = new RepositoryImpl<>(jdbcTemplate, Person.class);

        this.person = new Person(1L, "박재성", 10, "jason");
        this.personRepository.save(person);
    }

    @DisplayName("EntityManger으로 디비 조회시 결과를 반환한다.")
    @Test
    void findTest() {
        Person actual = entityManger.find(Person.class, 1L);

        assertThat(person).isEqualTo(actual);
    }

}
