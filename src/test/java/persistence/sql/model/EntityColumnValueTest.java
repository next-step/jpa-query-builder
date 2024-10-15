package persistence.sql.model;

import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

        List<String> fieldValues = entityColumnValues.stream()
                .map(EntityColumnValue::getValueInClause)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        assertTrue(fieldValues.stream().anyMatch(x -> x.equals(String.format("'%s'", name))));
        assertTrue(fieldValues.stream().anyMatch(x -> x.equals(String.valueOf(age))));
        assertTrue(fieldValues.stream().anyMatch(x -> x.equals(String.format("'%s'", email))));
    }

}
