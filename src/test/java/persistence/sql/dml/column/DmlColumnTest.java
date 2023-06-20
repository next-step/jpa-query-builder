package persistence.sql.dml.column;

import fixture.PersonV3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static fixture.PersonFixtures.createPerson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DmlColumnTest {
    @Test
    @DisplayName("컬럼 이름을 반환한다")
    public void columnName() throws NoSuchFieldException {
        PersonV3 person = createPerson();
        Field idField = person.getClass().getDeclaredField("id");
        Field nameField = person.getClass().getDeclaredField("name");
        Field ageField = person.getClass().getDeclaredField("age");

        DmlColumn idColumn = new DmlColumn(idField, person);
        DmlColumn nameColumn = new DmlColumn(nameField, person);
        DmlColumn ageColumn = new DmlColumn(ageField, person);

        assertAll(
                () -> assertThat(idColumn.name()).isEqualTo("id"),
                () -> assertThat(nameColumn.name()).isEqualTo("nick_name"),
                () -> assertThat(ageColumn.name()).isEqualTo("old")
        );
    }

    @Test
    @DisplayName("컬럼 값에 대한 문자열을 반환한다")
    public void columnValue() throws NoSuchFieldException {
        PersonV3 person = createPerson();
        Field idField = person.getClass().getDeclaredField("id");
        Field nameField = person.getClass().getDeclaredField("name");
        Field ageField = person.getClass().getDeclaredField("age");

        DmlColumn idColumn = new DmlColumn(idField, person);
        DmlColumn nameColumn = new DmlColumn(nameField, person);
        DmlColumn ageColumn = new DmlColumn(ageField, person);

        assertAll(
                () -> assertThat(idColumn.value()).isEqualTo("1"),
                () -> assertThat(nameColumn.value()).isEqualTo("'yohan'"),
                () -> assertThat(ageColumn.value()).isEqualTo("31")
        );
    }
}