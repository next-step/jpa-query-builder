package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.*;
import persistence.sql.ddl.exception.IncorrectIdFieldException;
import persistence.sql.ddl.exception.NotEntityException;
import persistence.sql.ddl.exception.NotFoundFieldException;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityFieldsTest {
    @Test
    void 클래스를_분석하여_Entity로_변경한다() {
        Class<NormalEntity> clazz = NormalEntity.class;

        EntityFields entityFields = EntityFields.from(clazz);

        assertAll(
            () -> assertThat(entityFields.tableName()).isEqualTo("NormalEntity"),
            () -> assertThat(entityFields.idField().field().name()).isEqualTo("id"),
            () -> assertThat(entityFields.getFieldNames()).containsExactlyInAnyOrder("name", "address")
        );
    }

    @Test
    void Table이_있으면_이름을_Table의_이름을_사용한다() {
        Class<TableEntity> clazz = TableEntity.class;

        EntityFields entityFields = EntityFields.from(clazz);

        assertThat(entityFields.tableName()).isEqualTo("table");
    }

    @Test
    void Entity_애노테이션이_없으면_실패한다() {
        Class<NotEntityAnnotationEntity> clazz = NotEntityAnnotationEntity.class;

        assertThatExceptionOfType(NotEntityException.class)
            .isThrownBy(() -> EntityFields.from(clazz));
    }

    @Test
    void Id가_0개이면_실패한다() {
        Class<EmptyIdEntity> clazz = EmptyIdEntity.class;

        assertThatExceptionOfType(IncorrectIdFieldException.class)
            .isThrownBy(() -> EntityFields.from(clazz));
    }

    @Test
    void Id가_2개_이상이면_실패한다() {
        Class<ManyIdsEntity> clazz = ManyIdsEntity.class;

        assertThatExceptionOfType(IncorrectIdFieldException.class)
            .isThrownBy(() -> EntityFields.from(clazz));
    }

    @Test
    void 필드_이름_전체를_가져온다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityFields entityFields = EntityFields.from(clazz);

        assertThat(entityFields.getFieldNames()).containsExactlyInAnyOrder("name", "address");
    }

    @Test
    void 이름으로_필드를_가져올_수_있다() throws NoSuchFieldException {
        Class<NormalEntity> clazz = NormalEntity.class;
        Field field = clazz.getDeclaredField("name");
        EntityFields entityFields = EntityFields.from(clazz);

        Field result = entityFields.getFieldByName("name");

        assertThat(result).isEqualTo(field);
    }

    @Test
    void 없는_이름으로_필드를_가져올시_실패한다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityFields entityFields = EntityFields.from(clazz);

        assertThatExceptionOfType(NotFoundFieldException.class)
            .isThrownBy(() -> entityFields.getFieldByName("fake"));
    }

    @Test
    void Id필드의_이름을_가져올_수_있다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityFields entityFields = EntityFields.from(clazz);

        String idFieldName = entityFields.getIdFieldName();

        assertThat(idFieldName).isEqualTo("id");
    }
}
