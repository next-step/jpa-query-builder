package hibernate.entity.column;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityColumnsTest {

    @Test
    void Transient가_없는_필드로_생성한다() {
        EntityColumns actual = new EntityColumns(TestEntity.class.getDeclaredFields());
        assertThat(actual.getValues()).hasSize(2);
    }

    @Test
    void EntityId를_반환한다() {
        EntityColumns givenEntityColumns = new EntityColumns(TestEntity.class.getDeclaredFields());
        EntityColumn actual = givenEntityColumns.getEntityId();
        assertThat(actual.isId()).isTrue();
    }

    @Test
    void EntityId가_없는_경우_예외가_발생한다() {
        EntityColumns givenEntityColumns = new EntityColumns(NoIdTestEntity.class.getDeclaredFields());
        assertThatThrownBy(givenEntityColumns::getEntityId)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("id field가 없습니다.");
    }

    static class TestEntity {
        @Id
        private Long id;

        @Column(name = "nick_name")
        private String name;

        @Transient
        private String email;
    }

    static class NoIdTestEntity {
        private String name;
    }
}
