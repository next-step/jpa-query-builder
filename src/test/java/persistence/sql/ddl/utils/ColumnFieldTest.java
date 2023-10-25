package persistence.sql.ddl.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.type.DataType;
import persistence.sql.ddl.type.H2DataType;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ColumnFieldTest {

    Class<Person> clazz = Person.class;

    @Test
    @DisplayName("Column name값이 있는 경우 테스트")
    void nameTest() throws Exception {
        Field name = clazz.getDeclaredField("name");
        ColumnType columnField = new ColumnField(name);
        assertThat(columnField.getName()).isEqualTo("nick_name");
    }

    @Test
    @DisplayName("Column name값이 없는 경우 테스트")
    void noNameTest() throws Exception {
        Field email = clazz.getDeclaredField("email");
        ColumnType columnField = new ColumnField(email);
        assertThat(columnField.getName()).isEqualTo("email");
    }

    @Test
    @DisplayName("isId 테스트")
    void isIdTest() throws Exception {
        Field email = clazz.getDeclaredField("email");
        ColumnType columnField = new ColumnField(email);
        assertThat(columnField.isId()).isFalse();
    }

    @Test
    @DisplayName("@Transient 붙어있을때 테스트")
    void transientTest() throws Exception {
        Field index = clazz.getDeclaredField("index");
        ColumnType columnField = new ColumnField(index);
        assertThat(columnField.isTransient()).isTrue();
    }

    @Test
    @DisplayName("@Transient 없을때 테스트")
    void noTransientTest() throws Exception {
        Field email = clazz.getDeclaredField("email");
        ColumnType columnField = new ColumnField(email);
        assertThat(columnField.isTransient()).isFalse();
    }

    @Test
    @DisplayName("nullable 테스트")
    void nullableTest() throws Exception {
        Field email = clazz.getDeclaredField("email");
        Field name = clazz.getDeclaredField("name");

        ColumnType emailField = new ColumnField(email);
        ColumnType nameField = new ColumnField(name);

        assertAll(
                () -> assertThat(emailField.isNullable()).isFalse(),
                () ->   assertThat(nameField.isNullable()).isTrue()
        );
    }
    @Test
    @DisplayName("length 테스트: 기본값은 255이다.")
    void getLengthTest() throws Exception {
        Field email = clazz.getDeclaredField("email");
        Field name = clazz.getDeclaredField("name");

        ColumnType emailField = new ColumnField(email);
        ColumnType nameField = new ColumnField(name);

        assertAll(
                () -> assertThat(emailField.getLength()).isEqualTo(255),
                () ->   assertThat(nameField.getLength()).isEqualTo(255)
        );
    }

    @Test
    @DisplayName("dataType 테스트")
    void dataTypeTest() throws Exception {
        Field email = clazz.getDeclaredField("email");
        Field age = clazz.getDeclaredField("age");

        ColumnType emailField = new ColumnField(email);
        ColumnType ageField = new ColumnField(age);

        System.out.println(emailField.getDataType());
        System.out.println(ageField.getDataType());
        assertAll(
                () -> assertThat(emailField.getDataType().getName()).isEqualTo("VARCHAR"),
                () ->   assertThat(ageField.getDataType().getName()).isEqualTo("INTEGER")
        );
    }

}