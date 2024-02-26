package persistence.sql.dml.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.db.H2Database;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryImplTest extends H2Database {

    private Person person1;
    private Person person2;

    private Repository<Person, Long> personRepository;

    @BeforeEach
    void setUp() {
        personRepository = new RepositoryImpl<>(jdbcTemplate, Person.class);
        personRepository.deleteAll();

        person1 = new Person(1L, "박재성", 10, "jason");
        person2 = new Person(2L, "이동규", 11, "cu");

        personRepository.save(person1);
        personRepository.save(person2);
    }

    @DisplayName("전체 목록을 반환한다.")
    @Test
    void findAllTest() {
        List<Person> result = personRepository.findAll();

        assertThat(result).isEqualTo(List.of(person1, person2));
    }

    @DisplayName("아이디값에 해당하는 값을 반환한다.")
    @Test
    void findByIdTest() {
        Optional<Person> person = personRepository.findById(1L);

        assertThat(person.get()).isEqualTo(person1);
    }

    @DisplayName("해당하는 아이디에 해당하는 Person 값을 삭제한다.")
    @Test
    void deleteIdTest() {
        personRepository.deleteById(1L);

        Optional<Person> person = personRepository.findById(1L);

        assertThat(person.isPresent()).isFalse();
    }
}
