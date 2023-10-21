package persistence.sql.dml.clause.builder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.operator.EqualOperator;
import persistence.sql.dml.clause.operator.NotEqualOperator;

@DisplayName("WHERE절 생성 테스트")
class WhereClauseBuilderTest {

    @Test
    @DisplayName("WHERE 절에 조건을 추가할 수 있다.")
    void canBuildWhereClause() {
        final String whereClause = WhereClauseBuilder
            .builder(WherePredicate.of("id", 2, new EqualOperator()))
            .build();

        assertThat(whereClause).isEqualTo("WHERE id = 2");
    }

    @Test
    @DisplayName("WHERE 절에 AND 조건으로 조건을 추가할 수 있다.")
    void canBuildWhereClauseWithAndCondition() {
        final String whereClause = WhereClauseBuilder
            .builder(WherePredicate.of("id", 2, new EqualOperator()))
            .and(WherePredicate.of("name", "james", new NotEqualOperator()))
            .build();

        assertThat(whereClause).isEqualTo("WHERE id = 2 AND name != 'james'");
    }

    @Test
    @DisplayName("WHERE 절에 OR 조건으로 조건을 추가할 수 있다.")
    void canBuildWhereClauseWithOrCondition() {
        final String whereClause = WhereClauseBuilder
            .builder(WherePredicate.of("id", 2, new EqualOperator()))
            .or(WherePredicate.of("name", "james", new NotEqualOperator()))
            .build();

        assertThat(whereClause).isEqualTo("WHERE id = 2 OR name != 'james'");
    }
}