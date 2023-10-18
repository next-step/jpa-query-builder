package hibernate.entity;

import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntityColumnFactoryTest {

    @Test
    void Id_어노테이션이_있는_경우_EntityId를_생성한다() throws NoSuchFieldException {
        EntityColumn actual = EntityColumnFactory.create(TestEntity.class.getDeclaredField("id"));
        assertThat(actual).isInstanceOf(EntityId.class);
    }

    @Test
    void Id_어노테이션이_없는_경우_EntityField를_생성한다() throws NoSuchFieldException {
        EntityColumn actual = EntityColumnFactory.create(TestEntity.class.getDeclaredField("name"));
        assertThat(actual).isInstanceOf(EntityField.class);
    }

    class TestEntity {
        @Id
        private Long id;

        private String name;
    }
}
