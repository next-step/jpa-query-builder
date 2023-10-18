package hibernate.entity;

import domain.Person;
import domain.Person2;
import hibernate.entity.EntityField;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    void Column_어노테이션의_nullable이_있는_경우_isNullable이_된다() throws NoSuchFieldException {
        boolean actual = new EntityField(Person2.class.getDeclaredField("email")).isNullable();
        assertThat(actual).isFalse();
    }

    @Test
    void Column_어노테이션의_nullable이_없는_경우_isNullable은_default값이_된다() throws NoSuchFieldException {
        boolean actual = new EntityField(Person.class.getDeclaredField("name")).isNullable();
        assertThat(actual).isTrue();
    }
}
