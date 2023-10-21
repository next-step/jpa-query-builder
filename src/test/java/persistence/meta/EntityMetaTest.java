package persistence.meta;


import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.exception.NoEntityException;
import persistence.testFixtures.NoHasEntity;
import persistence.testFixtures.Person;

class EntityMetaTest {

    @Test
    @DisplayName("엔티티가 비어 있으면 예외가 발생한다")
    void emptyEntity() {
       assertThatExceptionOfType(NoEntityException.class)
               .isThrownBy(() -> new EntityMeta(null));
    }

    @Test
    @DisplayName("엔티티 어노테이션이 없으면 예외를 발생한다.")
    void noEntity() {
        assertThatExceptionOfType(NoEntityException.class)
                .isThrownBy(() -> new EntityMeta(NoHasEntity.class));
    }

    @Test
    @DisplayName("엔티티 어노테이션이 없으면 예외를 발생한다.")
    void createEntityMeta() {
        EntityMeta entityMeta = new EntityMeta(Person.class);

        assertSoftly((it -> {
            it.assertThat(entityMeta.getTableName()).isEqualTo("users");
            it.assertThat(entityMeta.getEntityColumns()).hasSize(4);
        }));
    }
}
