package persistence.dialect;

import persistence.sql.dml.DmlGenerator;

import java.sql.Connection;

public class PersistenceEnvironment {
    private final PersistenceEnvironmentStrategy strategy;

    public PersistenceEnvironment(final PersistenceEnvironmentStrategy strategy) {
        this.strategy = strategy;
    }


    public Dialect getDialect() {
        return this.strategy.getDialect();
    }

    public Connection getConnection() {
        return this.strategy.getConnection();
    }

    public DmlGenerator getDmlGenerator() {
        return this.strategy.getDmlGenerator();
    }
}
