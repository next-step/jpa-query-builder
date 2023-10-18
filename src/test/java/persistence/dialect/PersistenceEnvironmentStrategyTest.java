package persistence.dialect;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceEnvironmentStrategyTest {

    @Test
    @DisplayName("PersistenceEnvironment.getDialect 의 default 는 H2Dialect 이다.")
    void persistenceEnvironmentStrategyTest() {
        final Dialect dialect = PersistenceEnvironment.getDialect();

        assertThat(dialect).isInstanceOf(H2Dialect.class);
    }

}
