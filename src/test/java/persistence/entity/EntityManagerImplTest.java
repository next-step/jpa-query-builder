package persistence.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.fixture.EntityWithId;
import persistence.fixture.EntityWithoutDefaultConstructor;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EntityManagerImplTest {
    @Test
    @DisplayName("엔티티를 조회한다.")
    void find() {
        // given
        final EntityManager<EntityWithId> entityManager = new EntityManagerImpl<>();

        // when
        final EntityWithId entityWithId = entityManager.find(EntityWithId.class, 1L);

        // then
        assertAll(
                () -> assertThat(entityWithId).isNotNull(),
                () -> assertThat(entityWithId.getId()).isNotNull(),
                () -> assertThat(entityWithId.getName()).isNotNull(),
                () -> assertThat(entityWithId.getAge()).isNotNull(),
                () -> assertThat(entityWithId.getEmail()).isNotNull(),
                () -> assertThat(entityWithId.getIndex()).isNull()
        );
    }

    @Test
    @DisplayName("기본 생성자가 없는 엔티티를 조회하면 예외를 발생한다.")
    void find_exception() {
        // given
        final EntityManager<EntityWithoutDefaultConstructor> entityManager = new EntityManagerImpl<>();

        // when & then
        assertThatThrownBy(() -> entityManager.find(EntityWithoutDefaultConstructor.class, 1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(CustomRowMapper.NO_DEFAULT_CONSTRUCTOR_FAILED_MESSAGE);
    }
}
