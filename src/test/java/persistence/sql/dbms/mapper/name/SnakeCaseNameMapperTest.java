package persistence.sql.dbms.mapper.name;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.entitymetadata.model.EntityTable;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnakeCaseNameMapperTest {
    @DisplayName("Entity class명을 snake case로 변환한다")
    @Test
    void create() {
        EntityTable<FakeVeryVeryLongNameEntity> entityTable = new EntityTable<>(FakeVeryVeryLongNameEntity.class);

        assertEquals("fake_very_very_long_name_entity", SnakeCaseNameMapper.INSTANCE.create(entityTable.getName()));
    }

    @ParameterizedTest
    @CsvSource({
            "fakeVeryVeryLongNameEntity, fake_very_very_long_name_entity",
            "HelloWorldJpa, hello_world_jpa",
            "NextStep, next_step",
    })
    void create_2(String name, String expected) {
        assertEquals(expected, SnakeCaseNameMapper.INSTANCE.create(name));
    }
}