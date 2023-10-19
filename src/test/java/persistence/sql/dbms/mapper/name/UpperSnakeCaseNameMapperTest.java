package persistence.sql.dbms.mapper.name;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.entitymetadata.model.EntityTable;

import static org.assertj.core.api.Assertions.assertThat;

class UpperSnakeCaseNameMapperTest {

    @DisplayName("Entity class명을 대문자 snake case로 변환한다")
    @Test
    void create() {
        UpperSnakeCaseNameMapper snakeCaseTableNameCreator = new UpperSnakeCaseNameMapper();
        EntityTable<FakeVeryVeryLongNameEntity> entityTable = new EntityTable<>(FakeVeryVeryLongNameEntity.class);

        assertThat(snakeCaseTableNameCreator.create(entityTable.getName())).isEqualTo("FAKE_VERY_VERY_LONG_NAME_ENTITY");
    }
}