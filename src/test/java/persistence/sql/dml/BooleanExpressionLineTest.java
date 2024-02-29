package persistence.sql.dml;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.*;

class BooleanExpressionLineTest {

    @Test
    void toQuery() {
        BooleanExpression expression = BooleanExpression.eq("column", 1);
        BooleanExpressionLine line = new BooleanExpressionLine(expression, LogicalOperator.AND);

        assertSoftly(softly -> {
            softly.assertThat(line.toQuery(true)).isEqualTo("column = 1");
            softly.assertThat(line.toQuery(false)).isEqualTo(" AND column = 1");
        });
    }
}
