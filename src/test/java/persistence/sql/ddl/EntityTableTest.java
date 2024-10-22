package persistence.sql.ddl;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.entity.*;
import persistence.sql.ddl.exception.IncorrectIdColumnException;
import persistence.sql.ddl.exception.NotEntityException;
import persistence.sql.ddl.exception.NotFoundColumnException;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityTableTest {
    @Test
    void 클래스를_분석하여_Entity로_변경한다() {
        Class<NormalEntity> clazz = NormalEntity.class;

        EntityTable entityTable = EntityTable.from(clazz);

        assertAll(
            () -> assertThat(entityTable.tableName()).isEqualTo("NormalEntity"),
            () -> assertThat(entityTable.getAllColumnNames()).containsExactlyInAnyOrder("id", "name", "address"),
            () -> assertThat(entityTable.idColumn().entityColumn().name()).isEqualTo("id"),
            () -> assertThat(entityTable.getColumnNames()).containsExactlyInAnyOrder("name", "address")
        );
    }

    @Test
    void Table이_있으면_이름을_Table의_이름을_사용한다() {
        Class<TableEntity> clazz = TableEntity.class;

        EntityTable entityTable = EntityTable.from(clazz);

        assertThat(entityTable.tableName()).isEqualTo("table");
    }

    @Test
    void Entity_애노테이션이_없으면_실패한다() {
        Class<NotEntityAnnotationEntity> clazz = NotEntityAnnotationEntity.class;

        assertThatExceptionOfType(NotEntityException.class)
            .isThrownBy(() -> EntityTable.from(clazz));
    }

    @Test
    void Id가_0개이면_실패한다() {
        Class<EmptyIdEntity> clazz = EmptyIdEntity.class;

        assertThatExceptionOfType(IncorrectIdColumnException.class)
            .isThrownBy(() -> EntityTable.from(clazz));
    }

    @Test
    void Id가_2개_이상이면_실패한다() {
        Class<ManyIdsEntity> clazz = ManyIdsEntity.class;

        assertThatExceptionOfType(IncorrectIdColumnException.class)
            .isThrownBy(() -> EntityTable.from(clazz));
    }

    @Test
    void 전체_필드_이름_전체를_가져온다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityTable entityTable = EntityTable.from(clazz);

        assertThat(entityTable.getAllColumnNames()).containsExactlyInAnyOrder("id", "name", "address");
    }

    @Test
    void 필드_이름_전체를_가져온다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityTable entityTable = EntityTable.from(clazz);

        assertThat(entityTable.getColumnNames()).containsExactlyInAnyOrder("name", "address");
    }

    @Test
    void 이름으로_필드를_가져올_수_있다() throws NoSuchFieldException {
        Class<NormalEntity> clazz = NormalEntity.class;
        Field field = clazz.getDeclaredField("name");
        EntityTable entityTable = EntityTable.from(clazz);

        Field result = entityTable.getFieldByColumnName("name");

        assertThat(result).isEqualTo(field);
    }

    @Test
    void 없는_이름으로_필드를_가져올시_실패한다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityTable entityTable = EntityTable.from(clazz);

        assertThatExceptionOfType(NotFoundColumnException.class)
            .isThrownBy(() -> entityTable.getFieldByColumnName("fake"));
    }

    @Test
    void Id필드의_이름을_가져올_수_있다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityTable entityTable = EntityTable.from(clazz);

        String idFieldName = entityTable.getNameOfIdColumn();

        assertThat(idFieldName).isEqualTo("id");
    }

    @Test
    void 객체를_생성한다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityTable entityTable = EntityTable.from(clazz);

        NormalEntity normalEntity = (NormalEntity) entityTable.newInstance();

        assertThat(normalEntity.getClass()).isEqualTo(clazz);
    }

    @Test
    void Id를_가지고_있으면_아이디_빈_여부는_거짓이다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityTable entityTable = EntityTable.from(clazz);
        NormalEntity normalEntity = new NormalEntity(10L, "soora", "soora", "fake");

        boolean result = entityTable.isEmptyId(normalEntity);

        assertThat(result).isFalse();
    }

    @Test
    void Id를_가지고_있지_않다면_아이디_빈_여부는_참이다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityTable entityTable = EntityTable.from(clazz);
        NormalEntity normalEntity = new NormalEntity(null, "soora", "soora", "fake");

        boolean result = entityTable.isEmptyId(normalEntity);

        assertThat(result).isTrue();
    }

    @Test
    void Id값을_가져올_수_있다() {
        Class<NormalEntity> clazz = NormalEntity.class;
        EntityTable entityTable = EntityTable.from(clazz);
        NormalEntity normalEntity = new NormalEntity(10L, "soora", "soora", "fake");

        Object id = entityTable.getId(normalEntity);

        assertThat(id).isEqualTo(10L);
    }
}
