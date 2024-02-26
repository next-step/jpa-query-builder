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

    @DisplayName("디비를 조회하여, 한건의 결과를 반환한다.")
    @Test
    void findTest() {
        Person actual = entityManger.find(Person.class, 1L);

        assertThat(person).isEqualTo(actual);
    }

    @DisplayName("디비에 데이터가 저장이된다.")
    @Test
    void insertTest() {
        Person newPerson = new Person(2L, "이동규", 11, "cu");
        entityManger.persist(newPerson);

        Person findPerson = entityManger.find(Person.class, 2L);
        assertThat(findPerson).isEqualTo(newPerson);
    }

}
