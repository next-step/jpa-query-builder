package persistence.sql.ddl;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class EntityTest {
    @Test
    public void 클래스를_분석하여_Entity로_변경한다() {
        Class<NormalEntity> clazz = NormalEntity.class;

        Entity entity = Entity.of(clazz);

        assertThat(entity.getIdField().getField().getName()).isEqualTo("id");
        assertThat(entity.getFields()).map(EntityField::getName).containsExactlyInAnyOrder("name", "address");
    }

    @Test
    public void Id가_0개이면_실패한다() {
        Class<EmptyIdEntity> clazz = EmptyIdEntity.class;

        assertThatExceptionOfType(IncorrectIdFieldException.class)
                .isThrownBy(() -> Entity.of(clazz));
    }

    @Test
    public void Id가_2개_이상이면_실패한다() {
        Class<ManyIdsEntity> clazz = ManyIdsEntity.class;

        assertThatExceptionOfType(IncorrectIdFieldException.class)
                .isThrownBy(() -> Entity.of(clazz));
    }
}
