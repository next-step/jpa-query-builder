package persistence.sql.dml.clause;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import persistence.sql.dml.clause.operator.Operator;
import persistence.sql.dml.clause.operator.PrecedenceOperator;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class WhereClauseTest {

    @DisplayName("WhereClause는 컬럼명, 값(숫자형), 연산자를 가지고 WHERE 절을 만들 수 있다.")
    @ParameterizedTest
    @ArgumentsSource(WhereClauseBuildClauseTestArgumentProvider.class)
    void buildClause(WhereClause whereClause, String expectedSQLWhereClause) {
        assertThat(whereClause.buildClause()).isEqualTo(expectedSQLWhereClause);
    }

    @DisplayName("WhereClause는 컬럼명, 값, 연산자를 가지고 WHERE 절을 만들 수 있다. (값이 문자열인 경우에는 ''로 감싸진다)")
    @ParameterizedTest
    @ArgumentsSource(WhereClauseBuildClauseTestStringTypesArgumentProvider.class)
    void buildClause_stringTypeValue(WhereClause whereClause, String expectedSQLWhereClause) {
        assertThat(whereClause.buildClause()).isEqualTo(expectedSQLWhereClause);
    }


    @Test
    void buildClauseWithoutPrecedence() {
        WhereClause whereClauseWithPrecedence = WhereClause.of("column1", "value1", Operator.EQUALS);

        assertThat(whereClauseWithPrecedence.buildClauseWithoutPrecedence()).isEqualTo("column1 = 'value1'");
    }

    static class WhereClauseBuildClauseTestArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(
                            WhereClause.of("column", 1, Operator.EQUALS),
                            " column = 1"
                    ),
                    Arguments.of(
                            WhereClause.of("column", 1, Operator.NOT_EQUALS),
                            " column != 1"
                    ),
                    Arguments.of(
                            WhereClause.of("column", 1, Operator.GREATER_THAN),
                            " column > 1"
                    ),
                    Arguments.of(
                            WhereClause.of("column", 1, Operator.GREATER_THAN_OR_EQUALS),
                            " column >= 1"
                    ),
                    Arguments.of(
                            WhereClause.of("column", 1, Operator.LESS_THAN),
                            " column < 1"
                    ),
                    Arguments.of(
                            WhereClause.of("column", 1, Operator.LESS_THAN_OR_EQUALS),
                            " column <= 1"
                    ),
                    Arguments.of(
                            WhereClause.of("column", null, Operator.IS_NULL),
                            " column IS NULL "
                    ),
                    Arguments.of(
                            WhereClause.of("column", null, Operator.IS_NOT_NULL),
                            " column IS NOT NULL "
                    )
            );
        }
    }

    static class WhereClauseBuildClauseTestStringTypesArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(
                            WhereClause.of("column", "value", Operator.EQUALS),
                            " column = 'value'"
                    ),
                    Arguments.of(
                            WhereClause.of("column", "value", Operator.NOT_EQUALS),
                            " column != 'value'"
                    ),
                    Arguments.of(
                            WhereClause.of("column", "value", Operator.GREATER_THAN),
                            " column > 'value'"
                    ),
                    Arguments.of(
                            WhereClause.of("column", "value", Operator.GREATER_THAN_OR_EQUALS),
                            " column >= 'value'"
                    ),
                    Arguments.of(
                            WhereClause.of("column", "value", Operator.LESS_THAN),
                            " column < 'value'"
                    ),
                    Arguments.of(
                            WhereClause.of("column", "value", Operator.LESS_THAN_OR_EQUALS),
                            " column <= 'value'"
                    ),
                    Arguments.of(
                            WhereClause.of("column", null, Operator.IS_NULL),
                            " column IS NULL "
                    ),
                    Arguments.of(
                            WhereClause.of("column", null, Operator.IS_NOT_NULL),
                            " column IS NOT NULL "
                    )
            );
        }
    }

    static class FakeWhereClause extends ChainingLogicalOperatorStandardWhereClause {

        public FakeWhereClause() {
            super(null, null, Operator.IS_NULL, PrecedenceOperator.NONE);
        }
    }
}
