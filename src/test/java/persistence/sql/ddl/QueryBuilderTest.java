package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    private static final QueryBuilder queryBuilder = new QueryBuilder();

    @Test
    @DisplayName("기본 Person 클래스를 이용한 DDL 생성 테스트")
    void DDLCreateTest() {
        // given
        String expectedQuery = "CREATE TABLE PERSON (ID BIGINT PRIMARY KEY, NAME VARCHAR(255), AGE INTEGER);";

        // when
        String actualQuery = queryBuilder.createTable(Person.class);

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }

    @Test
    @DisplayName("@GeneratedValue, @Column 추가된 Person 클래스를 이용한 DDL 생성 테스트")
    void DDLCreateTest2() {
        // given
        String expectedQuery = "CREATE TABLE PERSON2 (ID BIGINT PRIMARY KEY AUTO_INCREMENT, NICK_NAME VARCHAR(255), OLD INTEGER, EMAIL VARCHAR(255) NOT NULL);";

        // when
        String actualQuery = queryBuilder.createTable(Person2.class);

        // then
        assertThat(actualQuery).isEqualTo(expectedQuery);
    }
}
