package persistence.sql.dml.clause;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import persistence.model.EntityColumn;
import persistence.sql.dialect.Dialect;
import persistence.fixture.PersonWithTransientAnnotation;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindOptionUnitTest {
    private Dialect mockDialect;
    private EntityColumn column1;
    private EntityColumn column2;
    private WhereClause mockWhereClause1;
    private WhereClause mockWhereClause2;
    private FindOption findOption;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        mockDialect = Mockito.mock(Dialect.class);
        column1 = EntityColumn.build(
                PersonWithTransientAnnotation.class.getDeclaredField("name"),
                Optional.empty())
        ;
        column2 = EntityColumn.build(
                PersonWithTransientAnnotation.class.getDeclaredField("email"),
                Optional.empty()
        );

        mockWhereClause1 = new WhereClause(new EqualClause(column1, "foo"));
        mockWhereClause2 = new WhereClause(new EqualClause(column2, "test@test.com"));

        findOption = new FindOption(
                List.of(column1, column2),
                List.of(mockWhereClause1, mockWhereClause2));
    }

    @Test
    @DisplayName("joinWhereClauses 시 조건절이 있다면 조건절을 OR로 묶어서 SQL 쿼리를 생성한다.")
    void testJoinWhereClauses() {
        Mockito.when(mockDialect.getIdentifierQuoted(Mockito.anyString()))
                .thenAnswer(invocation -> "\"" + invocation.getArgument(0) + "\"");
        Mockito.when(mockDialect.getValueQuoted(Mockito.anyString()))
                .thenAnswer(invocation -> "'" + invocation.getArgument(0) + "'");

        String result = findOption.joinWhereClauses(mockDialect);

        assertEquals("WHERE (\"nick_name\" = 'foo') OR (\"email\" = 'test@test.com')", result);
    }

    @Test
    @DisplayName("joinWhereClauses 시 조건절이 없다면 빈 문자열을 반환한다.")
    void testJoinWhereClausesWithEmptyClauses() {
        FindOption emptyFindOption = new FindOption(List.of(column1, column2), List.of());

        String result = emptyFindOption.joinWhereClauses(mockDialect);

        assertEquals("", result);
    }
}
