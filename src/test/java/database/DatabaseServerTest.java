package database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseServerTest {
    @DisplayName("DB연결 확인.")
    @Test
    void connectDBTest() throws SQLException {
        //given
        final DatabaseServer server = new H2();

        //when
        server.start();

        //then
        try (Connection connection = server.getConnection()) {
            assertThat(connection).isNotNull();
        } finally {
            server.stop();
        }
    }

}