package persistence.sql.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.study.sql.ddl.Person1;
import persistence.study.sql.ddl.Person2;
import persistence.study.sql.ddl.Person3;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ColumnsTest {

    @DisplayName("올바른 컬럼 이름을 반환하는지 확인")
    @ParameterizedTest
    @MethodSource
    void getColumnNames(Columns columns, String[] columnName) {
        List<String> result = columns.getColumnNames();

        assertThat(result).containsExactlyInAnyOrder(columnName);
    }

    private static Stream<Arguments> getColumnNames() {
        Class<Person1> person1Class = Person1.class;
        Class<Person2> person2Class = Person2.class;
        Class<Person3> person3Class = Person3.class;

        Field[] person1Fields = person1Class.getDeclaredFields();
        Field[] person2Fields = person2Class.getDeclaredFields();
        Field[] person3Fields = person3Class.getDeclaredFields();

        Columns person1Columns = new Columns(person1Fields);
        Columns person2Columns = new Columns(person2Fields);
        Columns person3Columns = new Columns(person3Fields);

        return Stream.of(
                Arguments.arguments(person1Columns, new String[]{"age", "name"}),
                Arguments.arguments(person2Columns, new String[]{"old", "nick_name", "email"}),
                Arguments.arguments(person3Columns, new String[]{"old", "nick_name", "email"})
        );
    }
}
