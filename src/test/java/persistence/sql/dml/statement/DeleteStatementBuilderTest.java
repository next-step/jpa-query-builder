package persistence.sql.dml.statement;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.generator.fixture.PersonV3;
import persistence.sql.dialect.H2ColumnType;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.operator.EqualOperator;

@DisplayName("DELETE 문 생성 테스트")
class DeleteStatementBuilderTest {

    @Test
    @DisplayName("Where절을 통해 조건이 있는 DELETE 문을 생성할 수 있다.")
    void canBuildSelectStatementWhere() {
        final String selectStatement = DeleteStatementBuilder.builder()
            .delete(PersonV3.class, new H2ColumnType())
            .where(WherePredicate.of("id", 1L, new EqualOperator()))
            .build();

        assertThat(selectStatement).isEqualTo("DELETE FROM USERS WHERE id = 1");
    }
}