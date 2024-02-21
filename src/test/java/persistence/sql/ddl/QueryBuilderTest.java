package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    @Test
    @DisplayName("기본 Person 클래스를 이용한 DDL 생성 테스트")
    void DDLCreateTest() {
        // given
        String expectedQuery = "CREATE TABLE PERSON (ID BIGINT PRIMARY KEY, NAME VARCHAR(255), AGE INTEGER);";

        // when
        QueryBuilder queryBuilder = new QueryBuilder();
        String actualQuery = queryBuilder.createTable(Person.class);

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }
}
