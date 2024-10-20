package persistence.sql.dml.clause;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import persistence.model.EntityColumn;
import persistence.sql.dialect.Dialect;
import persistence.fixture.PersonWithTransientAnnotation;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhereClauseUnitTest {
    private Dialect mockDialect;

    private EntityColumn emailColumn;

    private EntityColumn nameColumn;

    @BeforeEach
    void setup() throws NoSuchFieldException {
        mockDialect = Mockito.mock(Dialect.class);

        Mockito.when(mockDialect.getIdentifierQuoted(Mockito.anyString()))
                .thenAnswer(invocation -> "\"" + invocation.getArgument(0) + "\"");

        Mockito.when(mockDialect.getValueQuoted(Mockito.anyString()))
                .thenAnswer(invocation -> "'" + invocation.getArgument(0) + "'");

        emailColumn = EntityColumn.build(
                PersonWithTransientAnnotation.class.getDeclaredField("email"),
                Optional.empty()
        );
        nameColumn = EntityColumn.build(
                PersonWithTransientAnnotation.class.getDeclaredField("name"),
                Optional.empty()
        );
    }

    @Test
    @DisplayName("하나의 Equal 조건만 가진 조건절의 SQL문을 생성한다.")
    void testToSqlWithSingleEqualClause() {
        // given
        EqualClause equalClause = new EqualClause(emailColumn, "test@test.com");
        WhereClause whereClause = new WhereClause(equalClause);

        // when
        String result = whereClause.toSql(mockDialect);

        // then
        assertEquals("(\"email\" = 'test@test.com')", result);
    }

    @Test
    @DisplayName("여러 Equal 조건을 가진 조건절을 AND로 묶어 SQL문을 생성한다.")
    void testToSqlWithMultipleEqualClauses() {
        // given
        EqualClause equalClause1 = new EqualClause(emailColumn, "test@test.com");
        EqualClause equalClause2 = new EqualClause(nameColumn, "홍길동");
        WhereClause whereClause = new WhereClause(Arrays.asList(equalClause1, equalClause2));

        // when
        String result = whereClause.toSql(mockDialect);

        // Then
        assertEquals("(\"email\" = 'test@test.com' AND \"nick_name\" = '홍길동')", result);
    }

    @Test
    @DisplayName("조건이 비어있다면 빈 문자열을 반환한다.")
    void testToSqlWithEmptyEqualClauses() {
        WhereClause whereClause = new WhereClause(Collections.emptyList());

        String result = whereClause.toSql(mockDialect);

        assertEquals("", result);
    }
}
