package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.entity.Person;

import static org.assertj.core.api.Assertions.assertThat;

class SelectQueryBuilderTest {

    private static final SelectQueryBuilder SELECT_QUERY_BUILDER = new SelectQueryBuilder();

    @Test
    @DisplayName("Person 객체로 SELECT 쿼리(findAll) 생성 테스트")
    void DMLSelect() {
        // given
        String expectedQuery = "SELECT ID, NICK_NAME, OLD, EMAIL FROM USERS;";

        // when
        String actualQuery = SELECT_QUERY_BUILDER.getSelectQueryString(Person.class);

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

}
