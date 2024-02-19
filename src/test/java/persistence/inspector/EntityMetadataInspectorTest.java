package persistence.inspector;

import jakarta.persistence.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EntityMetadataInspectorTest {

    private EntityMetadataInspector entityMetadataInspector;

    @BeforeEach
    private void setUp() {
        entityMetadataInspector = new EntityMetadataInspector(Person.class);
    }

    @Test
    @DisplayName("tableName 가져오기")
    void getTableName() {
        assertEquals("users", entityMetadataInspector.getTableName());
    }

    @Test
    @DisplayName("columns의 개수 가져오기")
    void getColumns() {
        assertThat(entityMetadataInspector.getColumns()).hasSize(4);
    }

}
