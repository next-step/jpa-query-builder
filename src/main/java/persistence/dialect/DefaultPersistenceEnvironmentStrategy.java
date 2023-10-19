package persistence.dialect;

import database.DatabaseServer;
import persistence.exception.PersistenceException;
import persistence.sql.dml.DmlGenerator;

import java.sql.Connection;
import java.sql.SQLException;

public class DefaultPersistenceEnvironmentStrategy implements PersistenceEnvironmentStrategy {
    private final DatabaseServer server;
    private final Dialect dialect;
    private final DmlGenerator dmlGenerator;

    public DefaultPersistenceEnvironmentStrategy(final DatabaseServer server, final Dialect dialect) {
        this.server = server;
        this.dialect = dialect;
        this.dmlGenerator = new DmlGenerator(dialect);
    }

    @Override
    public Dialect getDialect() {
        return this.dialect;
    }

    @Override
    public DmlGenerator getDmlGenerator() {
        return this.dmlGenerator;
    }

    @Override
    public Connection getConnection() {
        try {
            return this.server.getConnection();
        } catch (SQLException e) {
            throw new PersistenceException("커넥션 연결을 실패했습니다.", e);
        }
    }
}
