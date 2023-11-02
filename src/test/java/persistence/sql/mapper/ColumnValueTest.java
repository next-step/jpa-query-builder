package persistence.sql.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnValueTest {

    @Test
    @DisplayName("getter로 값 추출 테스트")
    void valueTest() {
        //when
        Person person = new Person();
        int AGE = 28;
        String NAME = "지영";
        String EMAIL = "jy@lim.com";
        person.setAge(AGE);
        person.setName(NAME);
        person.setEmail(EMAIL);

        //given
        Object name = new ColumnValue(person, "name").getValue();
        Object email = new ColumnValue(person, "email").getValue();
        Object age = new ColumnValue(person, "age").getValue();
        Object id = new ColumnValue(person, "id").getValue();

        //then
        assertAll(
                () -> assertThat(name).isEqualTo(NAME),
                () -> assertThat(email).isEqualTo(EMAIL),
                () -> assertThat(age).isEqualTo(AGE),
                () -> assertThat(id).isEqualTo(null)
        );
    }

    @Test
    @DisplayName("getter 가 없는 경우 테스트")
    void noGetterTest() {
        //when
        Person person = new Person();

        try {
            //given
            Object name = new ColumnValue(person, "address").getValue();
        } catch (RuntimeException e) {
            //then
            assertEquals("address에 getAddress가 존재하지 않습니다.", e.getMessage());
        }
    }
}