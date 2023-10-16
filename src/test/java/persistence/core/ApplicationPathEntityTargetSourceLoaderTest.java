package persistence.core;

import entityloaderfixture.StartEntityPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationPathEntityTargetSourceLoaderTest {

    @DisplayName("entityloaderfixture 패키지의 StartEntityPath 클래스로부터" +
            " 하위 Path를 통해 2개의 Entity클래스를 읽어 MetadataModel을 생성한다.")
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
