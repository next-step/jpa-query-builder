package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.entity.Person;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SelectQueryBuilderTest {

    @Test
    @DisplayName("Person 객체로 SELECT 쿼리(findAll) 생성 테스트")
    void DMLSelectTest() {
        // given
        String expectedQuery = "SELECT id, nick_name, old, email FROM users;";
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class, new ArrayList<>(), new ArrayList<>());
        // when
        String actualQuery = selectQueryBuilder.build();

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("Person 객체로 SELECT 쿼리(findById) 생성 테스트")
    void DMLSelect2Test() {
        // given
        String expectedQuery = "SELECT id, nick_name, old, email FROM users WHERE id = 1;";
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class, 1L);

        // when
        String actualQuery = selectQueryBuilder.build();

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("Person 객체로 SELECT 쿼리(findNameAndAge) 생성 테스트")
    void DMLSelect3Test() {
        // given
        String expectedQuery = "SELECT id, nick_name, old, email FROM users WHERE nick_name = 'jamie' AND old = 34;";
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(Person.class, List.of("name", "age"), List.of("jamie", 34));

        // when
        String actualQuery = selectQueryBuilder.build();

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("WHERE 절의 컬럼과 값의 개수가 일치하지 않는 경우 예외 발생 테스트")
    void DMLSelectFailTest() {
        // given
        String message = "The number of columns and values corresponding to the condition statement do not match.";

        // when & then
        assertThatThrownBy(() -> new SelectQueryBuilder(Person.class, List.of("name", "age"), List.of("jamie")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

}
