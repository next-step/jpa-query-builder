package persistence.sql.dml.clause.operator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.clause.WhereClause;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

class WhereClauseSQLBuilderTest {

    @DisplayName("SQL where절 생성")
    @Test
    void build() {
        WhereClause whereClause = WhereClause.of("column1", "value1", Operator.EQUALS).and(
                WhereClause.of("column2", "value2", Operator.NOT_EQUALS).or(
                        WhereClause.of("column3", 3, Operator.GREATER_THAN).and(
                                WhereClause.of("column4", 4, Operator.LESS_THAN_OR_EQUALS)
                        )
                )
        );
        WhereClauseSQLBuilder builder = new WhereClauseSQLBuilder(whereClause);
        assertThat(builder.build()).isEqualTo("WHERE (column1 = 'value1' AND (column2 != 'value2' OR (column3 > 3 AND column4 <= 4)))");
    }
}
