package persistence.dialect;

import persistence.sql.dml.DmlGenerator;

import java.sql.Connection;

public interface PersistenceEnvironmentStrategy {
    Dialect getDialect();
    DmlGenerator getDmlGenerator();
    Connection getConnection();
}
