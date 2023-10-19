package persistence.core;

import database.DatabaseServer;
import persistence.dialect.Dialect;
import persistence.exception.PersistenceException;

import java.sql.Connection;
import java.sql.SQLException;

public class DefaultPersistenceEnvironmentStrategy implements PersistenceEnvironmentStrategy {
    private final DatabaseServer server;
    private final Dialect dialect;

    public DefaultPersistenceEnvironmentStrategy(final DatabaseServer server, final Dialect dialect) {
        this.server = server;
        this.dialect = dialect;
    }

    @Override
    public Dialect getDialect() {
        return this.dialect;
    }

    @Override
    public Connection getConnection() {
        try {
            return this.server.getConnection();
        } catch (final SQLException e) {
            throw new PersistenceException("커넥션 연결을 실패했습니다.", e);
        }
    }
}
