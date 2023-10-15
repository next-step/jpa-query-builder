package persistence.core;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.FixtureEntity;
import persistence.core.EntityMetadata;
import persistence.exception.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityMetadataTest {
    private Class<?> mockClass;

    @Test
    @DisplayName("Entity 클래스를 이용해 EntityMetadata 인스턴스를 생성할 수 있다.")
    void entityMetadataCreateTest() {
        mockClass = FixtureEntity.WithId.class;
        final EntityMetadata<?> entityMetadata = new EntityMetadata<>(mockClass);
        assertResult(entityMetadata, "WithId", "id");
    }

    @Test
    @DisplayName("Entity 클래스에 @Entity 가 붙어있지 않으면 인스턴스 생성에 실패해야한다.")
    void entityMetadataCreateFailureTest() {
        mockClass = FixtureEntity.WithoutEntity.class;
        assertThatThrownBy(() -> new EntityMetadata<>(mockClass))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("Entity 클래스에 @Table 설정을 통해 tableName 을 설정해 인스턴스를 생성 할 수 있다..")
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