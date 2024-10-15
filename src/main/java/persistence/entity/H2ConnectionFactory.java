package persistence.entity;

import database.H2;

import java.sql.Connection;
import java.sql.SQLException;

public class H2ConnectionFactory {
    private static final String SERVER_CREATION_FAILED_MESSAGE = "서버 생성에 실패하였습니다.";
    private static final String CONNECTION_FAILED_MESSAGE = "서버 연결에 실패하였습니다.";

    private H2ConnectionFactory() {
        throw new AssertionError();
    }

    static Connection getConnection() {
        final H2 server = getServer();
        return cretaeConnection(server);
    }

    private static H2 getServer() {
        final H2 server;
        try {
            server = new H2();
        } catch (SQLException e) {
            throw new IllegalStateException(SERVER_CREATION_FAILED_MESSAGE, e);
        }
        return server;
    }

    private static Connection cretaeConnection(H2 server) {
        try {
            return server.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException(CONNECTION_FAILED_MESSAGE, e);
        }
    }
}
