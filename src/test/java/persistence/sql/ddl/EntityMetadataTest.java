package persistence.sql.ddl;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.FixtureEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityMetadataTest {
    private Class<?> mockClass;

    @Test
    @DisplayName("EntityMetadata 생성 테스트")
    void entityMetadataCreateTest() {
        mockClass = FixtureEntity.WithId.class;
        final EntityMetadata<?> entityMetadata = new EntityMetadata<>(mockClass);
        assertResult(entityMetadata, "WithId", "id");
    }

    @Test
    @DisplayName("EntityMetadata 생성 실패 테스트")
    void entityMetadataCreateFailureTest() {
        mockClass = FixtureEntity.WithoutEntity.class;
        assertThatThrownBy(() -> new EntityMetadata<>(mockClass))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("EntityMetadata Table 어노테이션 테스트")
    void tableAnnotatedEntityMetadataCreateTest() {
        mockClass = FixtureEntity.WithTable.class;
        final EntityMetadata<?> entityMetadata = new EntityMetadata<>(mockClass);
        assertResult(entityMetadata, "test_table", "id");
    }

    private void assertResult(final EntityMetadata<?> entityMetadata, final String withId, final String id) {
        assertThat(entityMetadata).isNotNull();
        assertThat(entityMetadata.getTableName()).isEqualTo(withId);
        assertThat(entityMetadata.getIdColumnName()).isEqualTo(id);
    }
}