package persistence.sql.dml.clause;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.dml.clause.operator.ComparisonOperator;
import persistence.sql.dml.clause.operator.EqualOperator;
import persistence.sql.dml.clause.operator.NotEqualOperator;

@DisplayName("WHERE 절 조건 생성 테스트")
class WherePredicateTest {

    @ParameterizedTest
    @DisplayName("Where절에 들어갈 비교 조건을 SQL로 변환할 수 있다.")
    @MethodSource(value = "comparisonOperatorPredicateArgument")
    void canConvertWherePredicateToCondition(ComparisonOperator operator, String conditionSql) {
        final WherePredicate predicate = WherePredicate.of("id", 1, operator);

        assertThat(predicate.toCondition()).isEqualTo(String.format("id %s 1", conditionSql));
    }

    static Stream<Arguments> comparisonOperatorPredicateArgument() {
        return Stream.of(
            Arguments.arguments(new EqualOperator(), "="),
            Arguments.arguments(new NotEqualOperator(), "!=")
        );
    }
}