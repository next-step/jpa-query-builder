package hibernate.entity.column;

import domain.Person;
import domain.Person2;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityFieldTest {

    @Test
    void Column_어노테이션의_name이_있는_경우_fieldName이_된다() throws NoSuchFieldException {
        String actual = new EntityField(Person2.class.getDeclaredField("name")).getFieldName();
        assertThat(actual).isEqualTo("nick_name");
    }

    @Test
    void Column_어노테이션의_name이_없는_경우_클래스명이_fieldName이_된다() throws NoSuchFieldException {
        String actual = new EntityField(Person.class.getDeclaredField("name")).getFieldName();
        assertThat(actual).isEqualTo("name");
    }

    @Test
    void Field의_ColumnType을_저장한다() throws NoSuchFieldException {
        ColumnType actual = new EntityField(Person.class.getDeclaredField("name")).getColumnType();
        assertThat(actual).isEqualTo(ColumnType.VAR_CHAR);
    }

    @Test
    void Column_어노테이션의_nullable이_있는_경우_isNullable이_된다() throws NoSuchFieldException {
        boolean actual = new EntityField(Person2.class.getDeclaredField("email")).isNullable();
        assertThat(actual).isFalse();
    }

    @Test
    void Column_어노테이션의_nullable이_없는_경우_isNullable은_default값이_된다() throws NoSuchFieldException {
        boolean actual = new EntityField(Person.class.getDeclaredField("name")).isNullable();
        assertThat(actual).isTrue();
    }

    @Test
    void isId는_false이다() throws NoSuchFieldException {
        boolean actual = new EntityField(Person.class.getDeclaredField("name")).isId();
        assertThat(actual).isFalse();
    }

    @Test
    void GenerationType을_반환하려하면_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityField(Person.class.getDeclaredField("name")).getGenerationType())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("일반 Field는 GenerationType을 호출할 수 없습니다.");
    }
}
