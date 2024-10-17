package persistence.sql.dml.clause;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import persistence.model.EntityColumn;
import persistence.sql.dialect.Dialect;
import persistence.sql.fixture.PersonWithTransientAnnotation;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EqualClauseUnitTest {
    private Dialect mockDialect;
    private EntityColumn column;
    private EqualClause equalClause;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        mockDialect = Mockito.mock(Dialect.class);
        column = EntityColumn.build(
                PersonWithTransientAnnotation.class.getDeclaredField("name"),
                Optional.empty()
        );

        Mockito.when(mockDialect.getIdentifierQuoted(Mockito.anyString()))
                .thenAnswer(invocation -> "\"" + invocation.getArgument(0) + "\"");

        Mockito.when(mockDialect.getValueQuoted(Mockito.anyString()))
                .thenAnswer(invocation -> "'" + invocation.getArgument(0) + "'");

        equalClause = new EqualClause(column, "foo");
    }

    @Test
    @DisplayName("컬럼과 찾는 값이 일치하는지에 대한 SQL문을 제공한다.")
    void testToSql() {
        String sql = equalClause.toSql(mockDialect);

        assertEquals("\"nick_name\" = 'foo'", sql);
    }
}
