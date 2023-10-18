package hibernate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityIdTest {

    @Test
    void Id_어노테이션이_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityId(NoIdField.class.getDeclaredField("field")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Id 어노테이션이 없습니다.");
    }

    @Test
    void GeneratedValue_어노테이션이_없는_경우_AUTO로_저장된다() throws NoSuchFieldException {
        GenerationType expected = GenerationType.AUTO;
        GenerationType actual = new EntityId(IdFieldNoGeneratedValue.class.getDeclaredField("id")).getGenerationType();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void GeneratedValue_어노테이션이_있는_경우_해당_값으로_저장된다() throws NoSuchFieldException {
        GenerationType expected = GenerationType.IDENTITY;
        GenerationType actual = new EntityId(IdField.class.getDeclaredField("id")).getGenerationType();
        assertThat(actual).isEqualTo(expected);
    }

    class NoIdField {
        private String field;
    }

    class IdFieldNoGeneratedValue {
        @Id
        private Long id;
    }

    class IdField {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    }
}
