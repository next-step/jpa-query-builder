package persistence.core;

import persistence.dialect.Dialect;

import java.sql.Connection;

public interface PersistenceEnvironmentStrategy {
    Dialect getDialect();
    Connection getConnection();
}
