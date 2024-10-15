
package persistence.sql.model;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
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

}
