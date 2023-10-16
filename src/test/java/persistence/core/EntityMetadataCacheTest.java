package persistence.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.FixtureEntity;
import persistence.exception.PersistenceException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class EntityMetadataCacheTest {

    private Class<?> mockClass;
    private EntityMetadataCache cache;

    @BeforeEach
    void setUp() {
        cache = EntityMetadataCache.getInstance();
    }

    @Test
    @DisplayName("EntityMetadataCache 를 통해 EntityMetadata 를 조회할 수 있다.")
    void entityMetadataCacheTest() {
        mockClass = FixtureEntity.WithId.class;
        final EntityMetadata<?> entityMetadata = cache.getEntityMetadata(mockClass);
        assertSoftly(softly -> {
            softly.assertThat(entityMetadata).isNotNull();
            softly.assertThat(entityMetadata.getTableName()).isEqualTo("WithId");
            softly.assertThat(entityMetadata.getIdColumnName()).isEqualTo("id");
        });
    }

    @Test
    @DisplayName("EntityMetadataCache 에 @Entity 가 붙어있지 않은 클래스를 조회할 시 Exception이 던져진다.")
    void entityMetadataCacheFailureTest() {
        mockClass = FixtureEntity.WithoutEntity.class;
        assertThatThrownBy(() -> cache.getEntityMetadata(mockClass))
                .isInstanceOf(PersistenceException.class);
    }

}
