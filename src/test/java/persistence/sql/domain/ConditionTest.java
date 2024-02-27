package persistence.sql.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConditionTest {

    @Test
    void should_return_condition() {
        String columnName = "test";
        String columnValue = "testValue";
        Condition condition = Condition.equal(new MockCondition(columnName, columnValue));

        assertThat(condition.getCondition()).isEqualTo(columnName + "=" + columnValue);
    }

}
