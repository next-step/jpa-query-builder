package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityMetadataExtractorTest {

    @Test
    @DisplayName("@Entity 없으면 Exception")
    public void noEntityAnnotation() {

        assertThatThrownBy(() -> {
            new EntityMetadataExtractor(NoEntityAnnotation.class);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No @Entity annotation");
    }

    private class NoEntityAnnotation {
    }

}
