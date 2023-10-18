package persistence.core;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.FixtureEntity;
import persistence.exception.PersistenceException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

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
    @DisplayName("Entity 클래스에 @Table 설정을 통해 tableName 을 설정해 인스턴스를 생성 할 수 있다.")
    void tableAnnotatedEntityMetadataCreateTest() {
        mockClass = FixtureEntity.WithTable.class;
        final EntityMetadata<?> entityMetadata = new EntityMetadata<>(mockClass);
        assertResult(entityMetadata, "test_table", "id");
    }

    @Test
    @DisplayName("Entity 클래스에 @Column(insertable) 설정을 통해 column insert 여부를 설정해 인스턴스를 생성 할 수 있다.")
    void withColumnNonInsertableEntityMetadataCreateTest() {
        mockClass = FixtureEntity.WithColumnNonInsertable.class;
        final EntityMetadata<?> entityMetadata = new EntityMetadata<>(mockClass);
        assertSoftly(softly -> {
            softly.assertThat(entityMetadata).isNotNull();
            softly.assertThat(entityMetadata.getTableName()).isEqualTo("WithColumnNonInsertable");
            softly.assertThat(entityMetadata.getIdColumnName()).isEqualTo("id");
            softly.assertThat(entityMetadata.getInsertableColumnNames()).containsExactly("insertableColumn");
        });
    }

    @Test
    @DisplayName("getColumnNames 를 통해 column 들의 이름들을 반환 받을 수 있다.")
    void entityMetadataGetColumnNamesTest() {
        mockClass = FixtureEntity.WithColumn.class;
        final EntityMetadata<?> entityMetadata = new EntityMetadata<>(mockClass);
        assertSoftly(softly -> {
            softly.assertThat(entityMetadata).isNotNull();
            softly.assertThat(entityMetadata.getTableName()).isEqualTo("WithColumn");
            softly.assertThat(entityMetadata.getIdColumnName()).isEqualTo("id");
            softly.assertThat(entityMetadata.getColumnNames()).containsExactly("id", "test_column", "notNullColumn");
        });
    }

    private void assertResult(final EntityMetadata<?> entityMetadata, final String withId, final String id) {
        assertSoftly(softly -> {
            softly.assertThat(entityMetadata).isNotNull();
            softly.assertThat(entityMetadata.getTableName()).isEqualTo(withId);
            softly.assertThat(entityMetadata.getIdColumnName()).isEqualTo(id);
        });
    }
}
