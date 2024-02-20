package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import java.sql.SQLException;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.h2.H2Dialect;

@DisplayName("DdlGenerator 통합 테스트")
class DdlGeneratorIntegrationTest {

    private DatabaseServer server;

    private JdbcTemplate jdbcTemplate;
    private DdlGenerator ddlGenerator;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();
        ddlGenerator = DdlGenerator.from(H2Dialect.getInstance());
        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @DisplayName("Person 테이블을 생성 후 삭제한다.")
    @Test
    void testCreateAndDropTable() {
        // when
        jdbcTemplate.execute(ddlGenerator.generateCreateQuery(Person.class));
        jdbcTemplate.execute(ddlGenerator.generateDropQuery(Person.class));
    }
}
