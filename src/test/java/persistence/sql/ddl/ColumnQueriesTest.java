package persistence.sql.ddl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DisplayName("ColumnQueries 의")
class ColumnQueriesTest {

    @Nested
    @DisplayName("toQuery 메서드는")
    class ToQuery {

        @Test
        @DisplayName("클래스의 필드값이 모두 담긴 쿼리를 만든다.")
        void fields() {
            //given
            Class<Person> personClass = Person.class;
            ColumnQueries columnQueries = ColumnQueries.of(personClass);
            List<String> fieldNames = Arrays.stream(personClass.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());

            //when
            String query = columnQueries.toQuery();

            //then
            Assertions.assertThat(query).contains(fieldNames);
        }
    }

}
