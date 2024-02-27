package persistence.sql.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class WhereTest {

    @Test
    void should_return_where_clause() {
        String tableName = "users";
        Where where = Where.from(tableName)
                .and(Condition.equal(new MockCondition("name", "'chansoo'")))
                .or(Condition.equal(new MockCondition("name", "'nextstep'")))
                .and(Condition.equal(new MockCondition("age", "29")));

        assertAll(
                () -> assertThat(where.getWhereClause()).isEqualTo("name='chansoo' or name='nextstep' and age=29"),
                () -> assertThat(where.getTableName()).isEqualTo(tableName)
        );
    }

    @Test
    void should_throw_exception_when_where_clause_not_valid() {
        assertThatThrownBy(() -> Where.from("users").getWhereClause())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("where clause is empty");
    }
}
