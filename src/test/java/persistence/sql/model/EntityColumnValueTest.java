package persistence.sql.model;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EntityColumnValueTest {

    @Test
    void 객체_생성_성공() {
        final String name = "이름";
        final Integer age = 11;
        final String email = "email@test.com";
        Person person = new Person(name, age, email, null);

        List<EntityColumnValue> entityColumnValues = Arrays.stream(person.getClass().getDeclaredFields())
                .map(field -> new EntityColumnValue(field, person))
                .collect(Collectors.toList());

        assertAll(() -> {
            assertTrue(entityColumnValues.stream().anyMatch(x -> x.getValue().equals(name)));
            assertTrue(entityColumnValues.stream().anyMatch(x -> x.getValue().equals(String.valueOf(age))));
            assertTrue(entityColumnValues.stream().anyMatch(x -> x.getValue().equals(email)));
        });
    }

}
