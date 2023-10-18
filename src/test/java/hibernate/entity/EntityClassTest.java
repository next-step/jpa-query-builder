package hibernate.entity;

import domain.Person;
import domain.Person3;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EntityClassTest {

    @Test
    void Entity_어노테이션이_없으면_생성_시_예외가_발생한다() {
        assertThatThrownBy(() -> new EntityClass(EntityClass.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Entity 어노테이션이 없는 클래스는 입력될 수 없습니다.");
    }

    @Test
    void Table_어노테이션의_name이_있으면_생성_시_tableName이_된다() {
        String actual = new EntityClass(Person3.class).tableName();
        assertThat(actual).isEqualTo("users");
    }

    @Test
    void Table_어노테이션의_name이_없으면_tableName은_클래스명이_된다() {
        String actual = new EntityClass(Person.class).tableName();
        assertThat(actual).isEqualTo("Person");
    }
}
