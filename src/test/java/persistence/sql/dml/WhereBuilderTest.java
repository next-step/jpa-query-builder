package persistence.sql.dml;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WhereBuilderTest {
    @Test
    void testToClause() {
        WhereBuilder whereBuilder = new WhereBuilder();
        whereBuilder.and(BooleanExpression.eq("id", 1));
        whereBuilder.and(BooleanExpression.eq("name", "testname"));
        assertThat(whereBuilder.toClause().toLowerCase()).isEqualTo("id = 1 and name = 'testname'");
    }

}
