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

@DisplayName("DefaultEntityManager 테스트")
class DefaultEntityManagerTest extends TestEntityInitialize {
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws SQLException {
        PersistenceConfig config = PersistenceConfig.getInstance();
        entityManager = config.entityManager();
    }

    @Test
    @DisplayName("persist 함수는 엔티티를 저장한다.")
    void testPersist() {
        // given
        PersonV3 person = new PersonV3("catsbi", 55, "casbi@naver.com", 123);

        // when
        entityManager.persist(person);

        // then
        PersonV3 actual = entityManager.find(PersonV3.class, 1L);

        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("persist 함수는 엔티티가 이미 존재하는 경우 병합을 수행한다.")
    void testPersistWithMerge() {
        // given
        PersonV3 person = new PersonV3("catsbi", 55, "casbi@naver.com", 123);

        // when
        entityManager.persist(person);
        PersonV3 actual = entityManager.find(PersonV3.class, 1L);

        entityManager.persist(actual);
        List<PersonV3> persons = entityManager.findAll(PersonV3.class);

        assertThat(persons).hasSize(1);
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