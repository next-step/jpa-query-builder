package persistence.sql.ddl;

import jakarta.persistence.GenerationType;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.TestEntity;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityIdColumnTest {
    @Test
    void GeneratedValue가_없으면_generationType은_Auto이다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("defaultId");

        EntityIdColumn entityIdColumn = EntityIdColumn.from(field);

        assertAll(
            () -> assertThat(entityIdColumn.generationType()).isEqualTo(GenerationType.AUTO),
            () -> assertThat(entityIdColumn.name()).isEqualTo("defaultId"),
            () -> assertThat(entityIdColumn.nullable()).isTrue()
        );
    }

    @Test
    void GeneratedValue가_있으면_generationType은_GeneratedValue의_stratgy를_사용한다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("identityId");

        EntityIdColumn entityIdColumn = EntityIdColumn.from(field);

        assertThat(entityIdColumn.generationType()).isEqualTo(GenerationType.IDENTITY);
    }
}
