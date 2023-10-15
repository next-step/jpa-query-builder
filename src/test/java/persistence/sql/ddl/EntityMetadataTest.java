package persistence.sql.ddl;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityMetadataTest {
    private Class<?> mockClass;

    @Test
    @DisplayName("EntityMetadata 생성 테스트")
    void entityMetadataCreateTest() {
        mockClass = MockEntity.WithId.class;
        final EntityMetadata<?> entityMetadata = new EntityMetadata<>(mockClass);
        assertThat(entityMetadata).isNotNull();
        assertThat(entityMetadata.getTableName()).isEqualTo("WithId");
    }

    @Test
    @DisplayName("EntityMetadata 생성 실패 테스트")
    void entityMetadataCreateFailureTest() {
        mockClass = MockEntity.WithoutEntity.class;
        assertThatThrownBy(() -> new EntityMetadata<>(mockClass))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("EntityMetadata Table 어노테이션 테스트")
    void tableAnnotatedEntityMetadataCreateTest() {
        mockClass = MockEntity.WithTable.class;
        final EntityMetadata<?> entityMetadata = new EntityMetadata<>(mockClass);
        assertThat(entityMetadata).isNotNull();
        assertThat(entityMetadata.getTableName()).isEqualTo("test_table");
    }
}