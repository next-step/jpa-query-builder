package persistence.sql.ddl.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReflectionUtilTest {

    Class<Person> clazz = Person.class;

    @Test
    @DisplayName("getter 메소드 추출 테스트")
    void getterTest() throws Exception {
        //given
        Field email = clazz.getDeclaredField("email");
        Field name = clazz.getDeclaredField("name");

        //then
        assertAll(() -> assertThat(ReflectionUtil.generateGetterMethodName(email)).isEqualTo("getEmail")
                ,()->assertThat(ReflectionUtil.generateGetterMethodName(name)).isEqualTo("getName")
       );
    }
}