package persistence.dialect;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceEnvironmentStrategyTest {

    @Test
    @DisplayName("PersistenceEnvironment 를 이용해 H2Dialect 전략을 선택 할 수 있다. ")
    void persistenceEnvironmentH2DialectStrategyTest() {
        final Dialect dialect = new PersistenceEnvironment(H2Dialect::new).getDialect();

        assertThat(dialect).isInstanceOf(H2Dialect.class);
    }

    @Test
    @DisplayName("PersistenceEnvironment 를 이용해 OracleDialect 전략을 선택 할 수 있다. ")
    void persistenceEnvironmentOracleDialectStrategyTest() {
        final Dialect dialect = new PersistenceEnvironment(OracleDialect::new).getDialect();

        assertThat(dialect).isInstanceOf(OracleDialect.class);
    }

}
