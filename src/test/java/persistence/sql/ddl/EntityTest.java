package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.*;
import persistence.sql.ddl.exception.IncorrectIdFieldException;
import persistence.sql.ddl.exception.NotEntityException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

public class EntityTest {
    @Test
    public void 클래스를_분석하여_Entity로_변경한다() {
        Class<NormalEntity> clazz = NormalEntity.class;

        Entity entity = Entity.of(clazz);

        assertAll(
                () -> assertThat(entity.getName()).isEqualTo("NormalEntity"),
                () -> assertThat(entity.getIdField().getField().getName()).isEqualTo("id"),
                () -> assertThat(entity.getFields()).map(EntityField::getName).containsExactlyInAnyOrder("name", "address")
        );
    }

    @Test
    public void Table이_있으면_이름을_Table의_이름을_사용한다() {
        Class<TableEntity> clazz = TableEntity.class;

        Entity entity = Entity.of(clazz);

        assertThat(entity.getName()).isEqualTo("table");
    }

    @Test
    public void Entity_애노테이션이_없으면_실패한다() {
        Class<NotEntityAnnotationEntity> clazz = NotEntityAnnotationEntity.class;

        assertThatExceptionOfType(NotEntityException.class)
                .isThrownBy(() -> Entity.of(clazz));
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
