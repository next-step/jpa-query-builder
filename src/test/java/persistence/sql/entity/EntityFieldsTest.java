package persistence.sql.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import persistence.sql.ddl.Person;
import persistence.sql.exception.ExceptionMessage;
import persistence.sql.exception.RequiredClassException;
import persistence.sql.model.EntityColumnName;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EntityFieldsTest {


    @ParameterizedTest
    @NullSource
    void 클래스_존재하지_않음(Class<?> clazz) {
        assertThatThrownBy(() -> new EntityFields(clazz))
                .isInstanceOf(RequiredClassException.class)
                .hasMessage(ExceptionMessage.REQUIRED_CLASS.getMessage());
    }

    @Test
    void DB_컬럼_조회() {
        Class<Person> personClass = Person.class;
        EntityFields entityFields = new EntityFields(personClass);

        List<Field> fields = entityFields.getPersistentFields();

        String nickname = "nick_name";
        String age = "old";
        String email = "email";

        assertAll(() -> {
            assertThat(fields.stream().anyMatch(x -> new EntityColumnName(x).getValue().equals(nickname))).isTrue();
            assertThat(fields.stream().anyMatch(x -> new EntityColumnName(x).getValue().equals(email))).isTrue();
            assertThat(fields.stream().anyMatch(x -> new EntityColumnName(x).getValue().equals(age))).isTrue();
        });
    }

    @Test
    void DB_ID_컬럼_조회() {
        Class<Person> personClass = Person.class;
        EntityFields entityFields = new EntityFields(personClass);

        List<Field> fields = entityFields.getIdFields();

        String id = "id";

        assertThat(fields.stream().anyMatch(x -> new EntityColumnName(x).getValue().equals(id))).isTrue();
    }
}
