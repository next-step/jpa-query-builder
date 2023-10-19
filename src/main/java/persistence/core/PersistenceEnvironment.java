package persistence.core;

import persistence.dialect.Dialect;
import persistence.sql.dml.DmlGenerator;

import java.sql.Connection;

public class PersistenceEnvironment {
    private final PersistenceEnvironmentStrategy strategy;
    private final DmlGenerator dmlGenerator;

    public PersistenceEnvironment(final PersistenceEnvironmentStrategy strategy) {
        this.strategy = strategy;
        this.dmlGenerator = new DmlGenerator(strategy.getDialect());
    }


    public Dialect getDialect() {
        return this.strategy.getDialect();
    }

    public Connection getConnection() {
        return this.strategy.getConnection();
    }

    public DmlGenerator getDmlGenerator() {
        return this.dmlGenerator;
    }
}
