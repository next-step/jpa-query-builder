package hibernate.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityIdTest {

    @Test
    void Id_어노테이션이_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityId(NoIdField.class.getDeclaredField("field")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Id 어노테이션이 없습니다.");
    }

    class NoIdField {
        private String field;
    }
}
