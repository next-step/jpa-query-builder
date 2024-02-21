package persistence.sql.ddl.dialect.database;

import database.DatabaseServer;
import database.H2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.dialect.DialectResolution;
import persistence.sql.ddl.dialect.DialectResolutionInfo;
import persistence.sql.ddl.dialect.exception.NotFoundDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DatabaseTest {

    private static DatabaseServer server;

    @BeforeAll
    static void setUp() throws Exception {
        server = new H2();
    }

    @DisplayName("h2 DB에 연결한다.")
    @Test
    void h2DataConnect() throws Exception {
        Database h2Database = Database.from(new DialectResolutionInfo(server.getConnection().getMetaData()));

        assertThat(h2Database.productNameMatchers("H2")).isTrue();
    }

    @DisplayName("지정되지 않은 mysql 연결시 에러가 발생한다.")
    @Test
    void mysqlDatabaseConnect() {
        assertThatThrownBy(() -> Database.from(MysqlDialectResolutionInfo()))
                .isInstanceOf(NotFoundDatabase.class)
                .hasMessage("찾을 수 없는 데이터베이스입니다.");
    }

    private DialectResolution MysqlDialectResolutionInfo() {
        return new DialectResolution() {
            @Override
            public String getDatabaseName() {
                return "MYSQL";
            }
        };
    }

}