package persistence.sql.mapper;

import jakarta.persistence.GenerationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.exception.InvalidIdColumnException;
import persistence.sql.mapper.ColumnId;
import persistence.sql.mapper.ColumnType;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ColumnIdTest {

    Person person = new Person();
    Class<? extends Person> personClass = person.getClass();

    @Test
    @DisplayName("Column name값이 있는 경우 테스트")
    void nameTest() throws Exception {
        Field id = personClass.getDeclaredField("id");
        ColumnType columnId = new ColumnId(person, id);
        Assertions.assertThat(columnId.getName()).isEqualTo("id");
    }

    @Test
    @DisplayName("pk column 판단")
    void noNameTest() throws Exception {
        Field id = personClass.getDeclaredField("id");
        ColumnType columnId = new ColumnId(person, id);
        Assertions.assertThat(columnId.isId()).isTrue();
    }

    @Test
    @DisplayName("pk column이 아닐때 에러 발생")
    void invalidIdColumnTest() throws Exception {
        Field name = personClass.getDeclaredField("name");
        assertThrows(InvalidIdColumnException.class, () -> {
            new ColumnId(person, name);
        });
    }

    @Test
    @DisplayName("GenerationType 테스트")
    void getGenerationType() throws Exception {
        Field id = personClass.getDeclaredField("id");

        ColumnId columnId = new ColumnId(person, id);

        assertThat(columnId.getGenerationType()).isEqualTo(GenerationType.IDENTITY);
    }
}