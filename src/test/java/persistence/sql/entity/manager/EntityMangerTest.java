package persistence.sql.entity.manager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.db.H2Database;
import persistence.sql.dml.repository.Repository;
import persistence.sql.dml.repository.RepositoryImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMangerTest extends H2Database {

    private EntityManger<Person, Long> entityManger;

    private Person person;

    @BeforeEach
    void setUp() {
        this.entityManger = new EntityManagerImpl<Person, Long>(jdbcTemplate);

        this.person = new Person(1L, "박재성", 10, "jason");

        entityManger.remove(person);
        entityManger.persist(person);
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

    @DisplayName("디비에 데이터가 삭제가 된다.")
    @Test
    void deleteTest() {
        entityManger.remove(person);

        Optional<Person> optionalPerson = Optional.ofNullable(entityManger.find(Person.class, person.getId()));

        assertThat(optionalPerson.isPresent()).isFalse();
    }

}
