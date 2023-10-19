package persistence.dialect;


import database.MockDatabaseServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceEnvironmentStrategyTest {

    private MockDatabaseServer databaseServer;

    @BeforeEach
    void setUp() {
        databaseServer = new MockDatabaseServer();
    }

    @Test
    @DisplayName("PersistenceEnvironment 를 이용해 H2Dialect 전략을 선택 할 수 있다. ")
    void persistenceEnvironmentH2DialectStrategyTest() {
        final PersistenceEnvironment persistenceEnvironment = new PersistenceEnvironment(new DefaultPersistenceEnvironmentStrategy(databaseServer, new H2Dialect()));

        assertThat(persistenceEnvironment.getDialect()).isInstanceOf(H2Dialect.class);
    }

    @Test
    @DisplayName("PersistenceEnvironment 를 이용해 OracleDialect 전략을 선택 할 수 있다. ")
    void persistenceEnvironmentOracleDialectStrategyTest() {
        final PersistenceEnvironment persistenceEnvironment = new PersistenceEnvironment(new DefaultPersistenceEnvironmentStrategy(databaseServer, new OracleDialect()));

        assertThat(persistenceEnvironment.getDialect()).isInstanceOf(H2Dialect.class);
    }

}
