package persistence.sql.ddl.utils;

import jakarta.persistence.GenerationType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.exception.InvalidIdColumnException;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ColumnIdTest {

    Class<Person> clazz = Person.class;

    @Test
    @DisplayName("Column name값이 있는 경우 테스트")
    void nameTest() throws Exception {
        Field id = clazz.getDeclaredField("id");
        ColumnType2 columnId = new ColumnId(id);
        Assertions.assertThat(columnId.getName()).isEqualTo("id");
    }

    @Test
    @DisplayName("pk column 판단")
    void noNameTest() throws Exception {
        Field id = clazz.getDeclaredField("id");
        ColumnType2 columnId = new ColumnId(id);
        Assertions.assertThat(columnId.isId()).isTrue();
    }

    @Test
    @DisplayName("pk column이 아닐때 에러 발생")
    void invalidIdColumnTest() throws Exception {
        Field name = clazz.getDeclaredField("name");
        assertThrows(InvalidIdColumnException.class, () -> {
            new ColumnId(name);
        });
    }

    @Test
    @DisplayName("GenerationType 테스트")
    void getGenerationType() throws Exception {
        Field id = clazz.getDeclaredField("id");

        ColumnId columnId = new ColumnId(id);

        assertThat(columnId.getGenerationType()).isEqualTo(GenerationType.IDENTITY);
    }
}