package persistence.sql.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import persistence.sql.ddl.Person;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EntityColumnNameTest {

    @Test
    void 객체_생성_성공() {
        final String name = "이름";
        final Integer age = 11;
        final String email = "email@test.com";
        Person person = new Person(name, age, email, null);

        List<Object> columnNames
                = Arrays.stream(person.getClass().getDeclaredFields()).map(field -> new EntityColumnName(field).getValue()).collect(Collectors.toList());

        assertThat(columnNames).contains("nick_name", "old", "email");
    }

    @ParameterizedTest
    @NullSource
    void 객체_생성시_Null_일때(Field field) {
        assertThatThrownBy(() -> new EntityColumnName(field))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("field가 존재하지 않습니다.");
    }


}
