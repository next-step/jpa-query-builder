package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.entity.Person;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SelectQueryBuilderTest {

    private static final SelectQueryBuilder SELECT_QUERY_BUILDER = new SelectQueryBuilder();

    @Test
    @DisplayName("Person 객체로 SELECT 쿼리(findAll) 생성 테스트")
    void DMLSelectTest() {
        // given
        String expectedQuery = "SELECT ID, NICK_NAME, OLD, EMAIL FROM USERS;";

        // when
        String actualQuery = SELECT_QUERY_BUILDER.getSelectQueryString(Person.class, new ArrayList<>(), new ArrayList<>());

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("Person 객체로 SELECT 쿼리(findById) 생성 테스트")
    void DMLSelect2Test() {
        // given
        String expectedQuery = "SELECT ID, NICK_NAME, OLD, EMAIL FROM USERS WHERE ID = 1;";

        // when
        String actualQuery = SELECT_QUERY_BUILDER.getSelectQueryString(Person.class, List.of("id"), List.of(1L));

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("Person 객체로 SELECT 쿼리(findNameAndAge) 생성 테스트")
    void DMLSelect3Test() {
        // given
        String expectedQuery = "SELECT ID, NICK_NAME, OLD, EMAIL FROM USERS WHERE NICK_NAME = 'jamie' AND OLD = 34;";

        // when
        String actualQuery = SELECT_QUERY_BUILDER.getSelectQueryString(Person.class, List.of("name", "age"), List.of("jamie", 34));

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("WHERE 절의 컬럼과 값의 개수가 일치하지 않는 경우 예외 발생 테스트")
    void DMLSelectFailTest() {
        // given
        String message = "The number of columns and values corresponding to the condition statement do not match.";

        // when & then
        assertThatThrownBy(() -> SELECT_QUERY_BUILDER.getSelectQueryString(Person.class, List.of("name", "age"), List.of("jamie")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }
}
