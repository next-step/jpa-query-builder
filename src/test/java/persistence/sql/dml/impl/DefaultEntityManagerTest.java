package persistence.sql.dml.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.config.PersistenceConfig;
import persistence.sql.dml.EntityManager;
import persistence.sql.dml.TestEntityInitialize;
import persistence.sql.fixture.PersonV3;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("DefaultEntityManager 테스트")
class DefaultEntityManagerTest extends TestEntityInitialize {
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws SQLException {
        PersistenceConfig config = PersistenceConfig.getInstance();
        entityManager = config.entityManager();
    }

    @Test
    @DisplayName("find 함수는 식별자가 유효한 경우 적절한 엔티티를 조회한다.")
    void testFind() {
        // given
        PersonV3 person = new PersonV3("catsbi", 55, "catsbi@naver.com", 123);
        entityManager.persist(person);

        // when
        PersonV3 actual = entityManager.find(PersonV3.class, 1L);
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("catsbi");
        assertThat(actual.getAge()).isEqualTo(55);
        assertThat(actual.getEmail()).isEqualTo("catsbi@naver.com");
        assertThat(actual.getIndex()).isNull();
    }

    @Test
    @DisplayName("find 함수는 식별자가 유효하지 않은 경우 null을 반환한다.")
    void testFindWithInvalidId() {
        // given
        PersonV3 person = new PersonV3("catsbi", 55, "catsbi@naver.com", 123);
        entityManager.persist(person);

        // when
        PersonV3 actual = entityManager.find(PersonV3.class, 999L);
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("find 함수는 식별자를 전달하지 않을 경우 예외를 던진다.")
    void testFindWithNullId() {
        // when, then
        assertThatThrownBy(() -> entityManager.find(PersonV3.class, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Primary key must not be null");
    }

    @Test
    @DisplayName("persist 함수는 엔티티를 저장한다.")
    void testPersist() {
        // given
        PersonV3 person = new PersonV3("catsbi", 55, "catsbi@naver.com", 123);

        // when
        entityManager.persist(person);

        // then
        PersonV3 actual = entityManager.find(PersonV3.class, 1L);

        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("persist 함수는 엔티티 매개변수를 전달하지 않을 경우 예외를 던진다.")
    void testPersistWithNullEntity() {
        // when, then
        assertThatThrownBy(() -> entityManager.persist(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Entity must not be null");
    }

    @Test
    @DisplayName("persist 함수는 엔티티가 이미 존재하는 경우 병합을 수행한다.")
    void testPersistWithMerge() {
        // given
        PersonV3 person = new PersonV3("catsbi", 25, "casbi@naver.com", 123);

        // when
        entityManager.persist(person);
        PersonV3 actual = entityManager.find(PersonV3.class, 1L);

        actual.setName("newCatsbi");
        actual.setAge(123);
        entityManager.persist(actual);
        List<PersonV3> persons = entityManager.findAll(PersonV3.class);

        assertThat(persons).hasSize(1);
        assertThat(persons.getFirst().getName()).isEqualTo("newCatsbi");
        assertThat(persons.getFirst().getAge()).isEqualTo(123);
    }

    @Test
    @DisplayName("merge 함수는 식별자가 이미 존재하는 Row의 식별자인 경우 엔티티를 병합한다.")
    void testMerge() {
        // given
        PersonV3 person = new PersonV3("catsbi", 55, "casbi@naver.com", 123);
        entityManager.persist(person);

        // when
        PersonV3 newPerson = new PersonV3(1L, "hansol", 33, "hansol@naver.com", 123);
        entityManager.merge(newPerson);

        // then
        PersonV3 mergedPerson = entityManager.find(PersonV3.class, 1L);
        assertThat(mergedPerson).isNotNull();
        assertThat(mergedPerson.getName()).isEqualTo("hansol");
        assertThat(mergedPerson.getAge()).isEqualTo(33);
        assertThat(mergedPerson.getEmail()).isEqualTo("hansol@naver.com");
    }

    @Test
    @DisplayName("merge 함수는 식별자가 존재하지 않는 Row의 식별자인 경우 엔티티를 저장한다.")
    void testMergeWithNewEntity() {
        // given
        PersonV3 person = new PersonV3("catsbi", 55, "casbi@naver.com", 123);
        entityManager.persist(person);

        PersonV3 foundPerson = entityManager.find(PersonV3.class, 1L);
        foundPerson.setId(2L);
        entityManager.merge(foundPerson);

        List<PersonV3> foundPersons = entityManager.findAll(PersonV3.class);
        assertThat(foundPersons).hasSize(2);
    }

        @Test
    @DisplayName("remove 함수는 엔티티를 삭제한다.")
    void testRemove() {
        // given
        PersonV3 person = new PersonV3("catsbi", 55, "casbi@naver.com", 123);

        // when
        entityManager.persist(person);
        List<PersonV3> persons = entityManager.findAll(PersonV3.class);

        assertThat(persons).hasSize(1);

        entityManager.remove(persons.getFirst());

        persons = entityManager.findAll(PersonV3.class);
        assertThat(persons).isEmpty();
    }
}