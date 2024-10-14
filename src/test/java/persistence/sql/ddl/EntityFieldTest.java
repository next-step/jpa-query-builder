package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.TestEntity;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityFieldTest {
    @Test
    void Column_어노테이션이_없는_필드를_통해_만들_경우_컬럼의_기본_값으로_객체를_생성한다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("name");

        EntityField entityField = EntityField.from(field);

        assertAll(
            () -> assertThat(entityField.field()).isEqualTo(field),
            () -> assertThat(entityField.name()).isEqualTo("name"),
            () -> assertThat(entityField.type()).isEqualTo(String.class),
            () -> assertThat(entityField.nullable()).isTrue(),
            () -> assertThat(entityField.length()).isEqualTo(255)
        );
    }

    @Test
    void Column_어노테이션이_있는_필드를_통해_만들_경우_컬럼의_설정_값으로_객체를_생성한다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("address");

        EntityField entityField = EntityField.from(field);

        assertAll(
            () -> assertThat(entityField.name()).isEqualTo("zip_address"),
            () -> assertThat(entityField.nullable()).isFalse(),
            () -> assertThat(entityField.length()).isEqualTo(10)
        );
    }

    @Test
    void Column_어노테이션이_있지만_name_이_설정이_안되어_있을_경우_필드의_이름으로_객체를_생성한다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("home");

        EntityField entityField = EntityField.from(field);

        assertAll(
            () -> assertThat(entityField.name()).isEqualTo("home"),
            () -> assertThat(entityField.type()).isEqualTo(Integer.class),
            () -> assertThat(entityField.nullable()).isFalse()
        );
    }

    @Test
    void 이름을_비교할_수_있으며_같으면_참이다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("address");

        EntityField entityField = EntityField.from(field);

        assertThat(entityField.isEqualName("zip_address")).isTrue();
    }

    @Test
    void 이름을_비교할_수_있으며_다르면_거짓이다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("address");

        EntityField entityField = EntityField.from(field);

        assertThat(entityField.isEqualName("address")).isFalse();
    }
}
