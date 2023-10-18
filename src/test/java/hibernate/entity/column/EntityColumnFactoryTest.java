package hibernate.entity.column;

import hibernate.entity.column.EntityColumn;
import hibernate.entity.column.EntityColumnFactory;
import hibernate.entity.column.EntityField;
import hibernate.entity.column.EntityId;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntityColumnFactoryTest {

    @Test
    void Transient_어노테이션이_있는_경우_생성불가로_판단한다() throws NoSuchFieldException {
        boolean actual = EntityColumnFactory.isAvailableCreateEntityColumn(TestEntity.class.getDeclaredField("age"));
        assertThat(actual).isFalse();
    }

    @Test
    void Transient_어노테이션이_없는_경우_생성가능으로_판단한다() throws NoSuchFieldException {
        boolean actual = EntityColumnFactory.isAvailableCreateEntityColumn(TestEntity.class.getDeclaredField("id"));
        assertThat(actual).isTrue();
    }

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

        @Transient
        private int age;
    }
}
