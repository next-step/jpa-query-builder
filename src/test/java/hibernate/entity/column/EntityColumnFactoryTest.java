package hibernate.entity.column;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class EntityColumnFactoryTest {

    @Test
    void Transient_어노테이션이_있는_경우_생성불가로_판단한다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("age");
        boolean actual = EntityColumnFactory.isAvailableCreateEntityColumn(givenField);
        assertThat(actual).isFalse();
    }

    @Test
    void Transient_어노테이션이_없는_경우_생성가능으로_판단한다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("id");
        boolean actual = EntityColumnFactory.isAvailableCreateEntityColumn(givenField);
        assertThat(actual).isTrue();
    }

    @Test
    void Id_어노테이션이_있는_경우_EntityId를_생성한다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("id");
        EntityColumn actual = EntityColumnFactory.create(givenField);
        assertThat(actual).isInstanceOf(EntityId.class);
    }

    @Test
    void Id_어노테이션이_없는_경우_EntityField를_생성한다() throws NoSuchFieldException {
        Field givenField = TestEntity.class.getDeclaredField("name");
        EntityColumn actual = EntityColumnFactory.create(givenField);
        assertThat(actual).isInstanceOf(EntityField.class);
    }

    static class TestEntity {
        @Id
        private Long id;

        private String name;

        @Transient
        private int age;
    }
}
