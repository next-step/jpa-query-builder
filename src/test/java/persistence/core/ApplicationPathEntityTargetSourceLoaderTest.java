package persistence.core;

import entityloaderfixture.StartEntityPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationPathEntityTargetSourceLoaderTest {

    @DisplayName("최초 Path 위치의 Class로 부터 패키지 탐색을 통해 EntityMetaDataModels를 생성한다")
    @Test
    void createEntityMetaDataModels() {
        // given
        EntityClassLoader entityClassLoader =
                new ApplicationPathEntityTargetSourceClassLoader(StartEntityPath.class);

        EntityMetadataModels entityMetaDataModels = entityClassLoader.getEntityMetadataModels();

        // when
        Set<EntityMetadataModel> metadataModels = entityMetaDataModels.getMetadataModels();

        //then
        assertThat(metadataModels).hasSize(2);
    }
}
