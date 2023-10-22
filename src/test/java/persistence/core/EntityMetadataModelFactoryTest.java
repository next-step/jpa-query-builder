package persistence.core;

import entityloaderfixture.PersonFixtureEntity;
import entityloaderfixture.depth.DepthPersonFixtureEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMetadataModelFactoryTest {

    private EntityMetadataModelFactory entityMetadataModelFactory;

    @BeforeEach
    void setUp() {
        entityMetadataModelFactory = new EntityMetadataModelFactory();
    }


    @DisplayName("2개의 Entity 클래스 데이터를 받아 EntityMetadataModel 2개를 가진 EntityMetadataModels를 생성한다")
    @Test
    void createEntityMetadataModels() {
        // given
        List<Class<?>> classes = List.of(PersonFixtureEntity.class, DepthPersonFixtureEntity.class);

        // when
        EntityMetadataModels entityMetadataModels = entityMetadataModelFactory.createEntityMetadataModels(
                classes
        );

        // then
        assertThat(entityMetadataModels.getMetadataModels()).hasSize(2);
    }

    @DisplayName("EntityMetadataModel에 target Entity가 전달되지 않을 경우 빈 EntityMetadataModels을 생성한다")
    @Test
    void createEmptyMetadataModels() {
        // given
        List<Class<?>> emptyList = List.of();

        // when
        EntityMetadataModels entityMetadataModels = entityMetadataModelFactory.createEntityMetadataModels(emptyList);

        // then
        assertThat(entityMetadataModels.getMetadataModels()).isEmpty();
    }

}
