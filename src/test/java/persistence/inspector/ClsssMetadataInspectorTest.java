package persistence.inspector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClsssMetadataInspectorTest {

    private ClsssMetadataInspector clsssMetadataInspector;

    @BeforeEach
    private void setUp() {
        clsssMetadataInspector = new ClsssMetadataInspector();
    }

    @Test
    @DisplayName("tableName 가져오기")
    void getTableName() {
        assertEquals("users", clsssMetadataInspector.getTableName(Person.class));
    }

    @Test
    @DisplayName("columns의 개수 가져오기")
    void getColumns() {
        assertThat(clsssMetadataInspector.getFields(Person.class)).hasSize(4);
    }

}
