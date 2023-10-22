package persistence.core;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import fixture.EntityMetadataModelFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EntityMetadataModelHolderTest {


    @DisplayName("요청한 Entity 타입에 맞는 EntityMetadataModel을 반환한다")
    @Test
    void getEntityMetadataModel() {
        // given
        EntityMetadataModel entityMetadataModel = EntityMetadataModelFixture.getEntityMetadataModel(DepthPersonFixtureEntity.class);
        EntityMetadataModels entityMetadataModels = new EntityMetadataModels(Set.of(entityMetadataModel));
        EntityMetadataModelHolder entityMetadataModelHolder = new EntityMetadataModelHolder(entityMetadataModels);

        // when
        EntityMetadataModel findEntityMetadataModel = entityMetadataModelHolder.getEntityMetadataModel(DepthPersonFixtureEntity.class);

        // then
        assertThat(findEntityMetadataModel).isEqualTo(entityMetadataModel);
    }

}
