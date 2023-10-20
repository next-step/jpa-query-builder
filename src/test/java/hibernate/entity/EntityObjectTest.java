package hibernate.entity;

import hibernate.entity.EntityObject;
import hibernate.entity.column.EntityColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EntityObjectTest {

    @Test
    void EntityId의_Value를_반환한다() {
        EntityObject givenEntityObject = new EntityObject(new TestEntity(1L, "최진영", "jinyoungchoi95@gmail.com"));
        Object actual = givenEntityObject.getEntityIdValue();
        assertThat(actual).isEqualTo(1L);
    }

    @Test
    void 필드명과_데이터가_담긴_Map을_반환한다() {
        TestEntity givenObject = new TestEntity(1L, "최진영", "jinyoungchoi95@gmail.com");
        Map<EntityColumn, Object> actual = new EntityObject(givenObject).getFieldValues();

        assert actual != null;
        assertAll(
                () -> assertThat(actual).hasSize(2),
                () -> assertThat(parseEntityColumnValue(actual, "id")).isEqualTo(1L),
                () -> assertThat(parseEntityColumnValue(actual, "nick_name")).isEqualTo("최진영")
        );
    }

    private Object parseEntityColumnValue(final Map<EntityColumn, Object> entityColumns, final String key) {
        return entityColumns.entrySet()
                .stream()
                .filter(entry -> entry.getKey().getFieldName().equals(key))
                .findAny()
                .map(Map.Entry::getValue)
                .get();
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
