package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("ColumnQuery 의")
class ColumnQueryTest {

    @Nested
    @DisplayName("toQuery 메서드는")
    class ToQuery {

        @Test
        @DisplayName("쿼리를 요청하면 이름과 타입이 포함된 쿼리를 반환한다.")
        void field() throws Exception {
            //given
            Class<Person> personClass = Person.class;
            String fieldName = "id";
            String fieldType = DataType.BIGINT.name();
            ColumnQuery columnQuery = ColumnQuery.of(personClass.getDeclaredField(fieldName));

            //when
            String query = columnQuery.toQuery();

            //then
            assertAll(
                    () -> assertThat(query).contains(fieldName),
                    () -> assertThat(query).contains(fieldType)
            );

        }

        @Test
        @DisplayName("PK 값의 쿼리를 요청히면 Primary Key를 붙인다.")
        void fieldWithPK() throws Exception {
            //given
            Class<Person> personClass = Person.class;
            ColumnQuery columnQuery = ColumnQuery.of(personClass.getDeclaredField("id"));

            //when
            String actual = columnQuery.toQuery();

            //then
            assertThat(actual).endsWith("PRIMARY KEY");
        }

        @Test
        @DisplayName("Nullable False 쿼리를 요청히면 NOT NULL을 붙인다.")
        void fieldWithNullableFalse() throws Exception {
            //given
            Class<Person> personClass = Person.class;
            ColumnQuery columnQuery = ColumnQuery.of(personClass.getDeclaredField("email"));

            //when
            String actual = columnQuery.toQuery();

            //then
            assertThat(actual).endsWith("NOT NULL");
        }
    }
}
