package persistence.sql.ddl;

import jakarta.persistence.GenerationType;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.TestEntity;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityIdFieldTest {
    @Test
    void GeneratedValue가_없으면_generationType은_Auto이다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("defaultId");

        EntityIdField entityIdField = EntityIdField.of(field);

        assertAll(
                () -> assertThat(entityIdField.getGenerationType()).isEqualTo(GenerationType.AUTO),
                () -> assertThat(entityIdField.getField().getName()).isEqualTo("defaultId"),
                () -> assertThat(entityIdField.getField().isNullable()).isTrue()
        );
    }

    @Test
    void GeneratedValue가_있으면_generationType은_GeneratedValue의_stratgy를_사용한다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("identityId");

        EntityIdField entityIdField = EntityIdField.of(field);

        assertThat(entityIdField.getGenerationType()).isEqualTo(GenerationType.IDENTITY);
    }
}
