package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityMetadataTest {

    @Test
    @DisplayName("@Entity 없으면 Exception")
    public void noEntityAnnotation() {

        assertThatThrownBy(() -> {
            new EntityMetadata(NoEntityAnnotation.class);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("No @Entity annotation");
    }

    private class NoEntityAnnotation {
    }

}
