package persistence.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.model.Column;
import persistence.study.sql.ddl.Person2;
import persistence.study.sql.ddl.Person3;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EntityBinderTest {

    private final Person3 person = new Person3(1L, "ASD", 100, "email@email.com");
    private final EntityBinder entityBinder = new EntityBinder(person);

    @DisplayName("올바르게 인스턴스의 값을 반환하는지 확인")
    @Test
    void getValue() throws NoSuchFieldException {
        Column column = createPerson3NameColumn();

        Object result = entityBinder.getValue(column);

        assertThat(result).isEqualTo("ASD");
    }

    @DisplayName("해당 컬럼이 없는 인스턴스의 값을 가져오려는 경우 IllegalArgumentException을 던진다.")
    @Test
    void getValueWithException() throws NoSuchFieldException {
        Column column = createPerson3NameColumn();

        EntityBinder entityBinder = new EntityBinder(new Person2("aa", 12, "qweqw"));
        assertThatThrownBy(() -> entityBinder.getValue(column))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("인스턴스에 정상적으로 값을 바인딩하는지 확인")
    @Test
    void setValue() throws Exception {
        Column column = createPerson3NameColumn();

        entityBinder.bindValue(column, "aaa");

        String result = person.getName();
        assertThat(result).isEqualTo("aaa");
    }

    @DisplayName("인스턴스에 잘못된 값을 바인딩하려는 경우 IllegalArgumentException을 던진다.")
    @Test
    void setValueWithWrongType() throws Exception {
        Column column = createPerson3NameColumn();

        assertThatThrownBy(() -> entityBinder.bindValue(column, 23))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Column createPerson3NameColumn() throws NoSuchFieldException {
        Class<Person3> clazz = Person3.class;
        Field field = clazz.getDeclaredField("name");
        return new Column(field);
    }
}
