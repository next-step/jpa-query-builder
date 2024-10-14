package database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseServerTest {


    @DisplayName("DB연결을 확인한다.")
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
