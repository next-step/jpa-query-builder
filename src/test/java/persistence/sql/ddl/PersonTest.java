package persistence.sql.ddl;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTest {
    Class<Person> personClass = Person.class;

    private static final Logger logger = LoggerFactory.getLogger(PersonTest.class);

    @Test
    @DisplayName("테이블의 이름을 가져온다")
    void getTableName() {
        String tableName = String.valueOf(personClass.getAnnotation(Table.class).name());
        assertThat(tableName).isEqualTo("users");
    }

    @Test
    void getColumnAnnotationValue() throws NoSuchFieldException {
        Field field1 = personClass.getDeclaredField("name");
        Column column = field1.getAnnotation(Column.class);
        String columnName = column.name();
        System.out.println(columnName);
    }
}

