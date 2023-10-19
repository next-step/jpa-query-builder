package hibernate.entity.column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityIdTest {

    @Test
    void Id_어노테이션이_없는_경우_예외가_발생한다() throws NoSuchFieldException {
        Field givenField = NoIdField.class.getDeclaredField("field");

        assertThatThrownBy(() -> new EntityId(givenField))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Id 어노테이션이 없습니다.");
    }

    @Test
    void GeneratedValue_어노테이션이_없는_경우_AUTO로_저장된다() throws NoSuchFieldException {
        Field givenField = IdFieldNoGeneratedValue.class.getDeclaredField("id");
        GenerationType expected = GenerationType.AUTO;

        GenerationType actual = new EntityId(givenField).getGenerationType();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void GeneratedValue_어노테이션이_있는_경우_해당_값으로_저장된다() throws NoSuchFieldException {
        Field givenField = IdField.class.getDeclaredField("id");
        GenerationType expected = GenerationType.IDENTITY;

        GenerationType actual = new EntityId(givenField).getGenerationType();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void isId는_true이다() throws NoSuchFieldException {
        Field givenField = IdField.class.getDeclaredField("id");
        boolean actual = new EntityId(givenField).isId();
        assertThat(actual).isTrue();
    }

    static class NoIdField {
        private String field;
    }

    static class IdFieldNoGeneratedValue {
        @Id
        private Long id;
    }

    static class IdField {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    }
}
