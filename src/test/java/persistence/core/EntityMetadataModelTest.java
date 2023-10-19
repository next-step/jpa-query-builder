package persistence.core;

import entityloaderfixture.depth.DepthPersonFixtureEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityMetadataModelTest {

    @DisplayName("Entity 클래스로부터 EntityMetadataModel을 생성한다")
    @Test
    void createEntityMetadataModel() {
        // given
        Class<DepthPersonFixtureEntity> personFixtureEntityClass = DepthPersonFixtureEntity.class;

        List<EntityColumn> entityColumns = Arrays.stream(personFixtureEntityClass.getDeclaredFields())
                .map(EntityColumn::new)
                .collect(Collectors.toUnmodifiableList());

        // when
        EntityMetadataModel entityMetadataModel = new EntityMetadataModel(
                personFixtureEntityClass.getSimpleName(),
                personFixtureEntityClass,
                entityColumns
        );

        // EntityMetadataModel은 Primarykey Column을 추출해서 따로 가지고 있음으로 생성한 EntityColumn에서 기분키 컬럼 제거
        entityColumns.remove(entityMetadataModel.getPrimaryKeyColumn());
        Set<EntityColumn> notContainsPrimaryKeyColumn = Set.copyOf(entityColumns);

        // then
        assertAll(
                () -> assertThat(entityMetadataModel.getTableName()).isEqualTo(personFixtureEntityClass.getSimpleName()),
                () -> assertThat(entityMetadataModel.getColumns()).hasSize(2),
                () -> assertThat(entityMetadataModel.getColumns()).containsAll(notContainsPrimaryKeyColumn),
                () -> assertThat(entityMetadataModel.getPrimaryKeyColumn().getName()).isEqualTo("id")
        );
    }
}
