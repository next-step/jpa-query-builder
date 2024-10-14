package persistence.sql.dml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.fixture.PersonWithTransientAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DmlQueryBuilderTest {
    DmlQueryBuilder queryBuilder;

    @BeforeEach
    void setup() {
        queryBuilder = new DmlQueryBuilder(new H2Dialect());
    }

    @Test
    @DisplayName("모든 필드의 값이 채워진 객체의 insert문을 생성한다.")
    void testCreateInsertQueryWithAllColumnsSet() {
        String expectedQuery = "INSERT INTO \"users\" " +
                "(\"id\", \"nick_name\", \"old\", \"email\") " +
                "VALUES (1, '홍길동', 20, 'test@test.com')";

        PersonWithTransientAnnotation user = new PersonWithTransientAnnotation(
                1L, "홍길동", 20, "test@test.com", 1
        );
        String resultQuery = queryBuilder.getInsertQuery(user);

        assertEquals(expectedQuery, resultQuery);
    }

    @Test
    @DisplayName("id가 없는 객체의 insert문을 생성한다.")
    void testCreateInsertQueryWithoutId() {
        String expectedQuery = "INSERT INTO \"users\" " +
                "(\"nick_name\", \"old\", \"email\") " +
                "VALUES ('홍길동', 20, 'test@test.com')";

        PersonWithTransientAnnotation user = new PersonWithTransientAnnotation(
                "홍길동", 20, "test@test.com", 1
        );
        String resultQuery = queryBuilder.getInsertQuery(user);

        assertEquals(expectedQuery, resultQuery);
    }

    @Test
    @DisplayName("null이 포함된 객체의 insert문을 생성한다.")
    void testCreateInsertQueryWithNull() {
        String expectedQuery = "INSERT INTO \"users\" " +
                "(\"nick_name\", \"old\", \"email\") " +
                "VALUES (NULL, NULL, 'test@test.com')";

        PersonWithTransientAnnotation user = new PersonWithTransientAnnotation("test@test.com");
        String resultQuery = queryBuilder.getInsertQuery(user);

        assertEquals(expectedQuery, resultQuery);
    }

    @Test
    @DisplayName("조건절 없이 테이블의 모든 레코드 조회")
    void testCreateSelectQueryWithoutClauses() {
        String expectedQuery = "SELECT * FROM \"users\"";

        String resultQuery =  queryBuilder.getSelectQuery();

        assertEquals(expectedQuery, resultQuery);
    }

    @Test
    @DisplayName("조건절로 테이블의 모든 레코드 조회")
    void testCreateSelectQueryWithClauses() {
        String expectedQuery = "SELECT * FROM \"users\"" +
                "WHERE (\"email\" = 'test@test.com' AND \"nick_name\" = '홍길동')" +
                "OR (\"id\" = 1)";

        String resultQuery =  queryBuilder.getSelectQuery(); // ADD parameters later...

        assertEquals(expectedQuery, resultQuery);
    }
}
