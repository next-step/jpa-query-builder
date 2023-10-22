package hibernate.entity;

import hibernate.entity.column.EntityColumn;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityClassTest {

    @Test
    void Entity_어노테이션이_없으면_생성_시_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityClass<>(EntityClass.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Entity 어노테이션이 없는 클래스는 입력될 수 없습니다.");
    }

    @Test
    void Table_어노테이션의_name이_있으면_생성_시_tableName이_된다() {
        String actual = new EntityClass<>(TableEntity.class).tableName();
        assertThat(actual).isEqualTo("new_table");
    }

    @Test
    void Table_어노테이션의_name이_없으면_tableName은_클래스명이_된다() {
        String actual = new EntityClass<>(NoTableEntity.class).tableName();
        assertThat(actual).isEqualTo("NoTableEntity");
    }

    @Test
    void 새로운_인스턴스를_생성한다()  {
        TableEntity actual = new EntityClass<>(TableEntity.class).newInstance();
        assertThat(actual).isInstanceOf(TableEntity.class);
    }

    @Test
    void 인스턴스_생성_시_기본생성자가_존재하지않으면_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityClass<>(NoConstructorEntity.class).newInstance())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("기본 생성자가 존재하지 않습니다.");
    }

    @Test
    void 필드명과_데이터가_담긴_Map을_반환한다() {
        TestEntity givenObject = new TestEntity(1L, "최진영", "jinyoungchoi95@gmail.com");
        Map<EntityColumn, Object> actual = new EntityClass<>(TestEntity.class).getFieldValues(givenObject);

        assert actual != null;
        assertAll(
                () -> assertThat(actual).hasSize(2),
                () -> assertThat(parseEntityColumnValue(actual, "id")).isEqualTo(1L),
                () -> assertThat(parseEntityColumnValue(actual, "nick_name")).isEqualTo("최진영")
        );
    }

    @Test
    void 다른_타입의_인스턴스_필드값을_반환하려하는_경우_예외가_발생한다() {
        TestEntity givenObject = new TestEntity(1L, "최진영", "jinyoungchoi95@gmail.com");
        EntityClass<TableEntity> givenEntityClass = new EntityClass<>(TableEntity.class);
        assertThatThrownBy(() -> givenEntityClass.getFieldValues(givenObject))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("EntityClass와 일치하지 않는 객체입니다.");
    }

    private Object parseEntityColumnValue(final Map<EntityColumn, Object> entityColumns, final String key) {
        return entityColumns.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getFieldName().equals(key))
                .findAny()
                .map(Map.Entry::getValue)
                .get();
    }

    @Test
    void entity객체에서_id를_추출한다() {
        TestEntity givenEntity = new TestEntity(1L, "최진영", "jinyoungchoi95@gmail.com");
        Object actual = new EntityClass<>(TestEntity.class).extractEntityId(givenEntity);
        assertThat(actual).isEqualTo(givenEntity.id);
    }

    @Test
    void 다른_타입의_entity객체에서_id추출_시_예외가_발생한다() {
        TableEntity givenEntity = new TableEntity(1L);
        assertThatThrownBy(() -> new EntityClass<>(TestEntity.class).extractEntityId(givenEntity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("EntityClass와 일치하지 않는 객체입니다.");
    }


    @Entity
    @Table(name = "new_table")
    static class TableEntity {
        @Id
        private Long id;

        public TableEntity() {
        }

        public TableEntity(Long id) {
            this.id = id;
        }
    }

    @Entity
    static class NoTableEntity {
        @Id
        private Long id;
    }

    @Entity
    static class NoConstructorEntity {
        @Id
        private Long id;

        public NoConstructorEntity(Long id) {
            this.id = id;
        }
    }

    @Entity
    static class TestEntity {
        @Id
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Transient
        private String email;

        public TestEntity() {
        }

        public TestEntity(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }
}
