package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.TestEntity;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityFieldTest {
    @Test
    public void Column_어노테이션이_없는_필드를_통해_만들_경우_컬럼의_기본_값으로_객체를_생성한다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("name");

        EntityField entityField = EntityField.of(field);

        assertThat(entityField.getName()).isEqualTo("name");
        assertThat(entityField.isNullable()).isTrue();
    }

    @Test
    public void Column_어노테이션이_있는_필드를_통해_만들_경우_컬럼의_설정_값으로_객체를_생성한다() throws NoSuchFieldException {
        Field field = TestEntity.class.getDeclaredField("address");

        EntityField entityField = EntityField.of(field);

        assertThat(entityField.getName()).isEqualTo("zip_address");
        assertThat(entityField.isNullable()).isFalse();
    }
}
