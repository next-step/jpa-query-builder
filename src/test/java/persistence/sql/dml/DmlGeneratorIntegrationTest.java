package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import java.sql.SQLException;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.ddl.DdlGenerator;
import persistence.sql.dialect.h2.H2Dialect;

@DisplayName("DmlGenerator 통합 테스트")
class DmlGeneratorIntegrationTest {

    private DatabaseServer server;

    private JdbcTemplate jdbcTemplate;
    private DdlGenerator ddlGenerator;
    private DmlGenerator dmlGenerator;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        ddlGenerator = DdlGenerator.from(H2Dialect.getInstance());
        dmlGenerator = DmlGenerator.from();

        jdbcTemplate.execute(ddlGenerator.generateCreateQuery(Person.class));
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(ddlGenerator.generateDropQuery(Person.class));
        server.stop();
    }

    @DisplayName("InsertQueryBuilder는 Insert 쿼리를 실행한다.")
    @CsvSource({
        "user1, 1, test1@abc.com, 1",
        "user2, 2, test2@abc.com, 2",
        "user3, 3, test3@abc.com, 3",
        "user4, 4, test4@abc.com, 4"
    })
    @ParameterizedTest
    void insertQueryTest(String name, int age, String email, int index) {
        Person person = new Person(name, age, email, index);
        jdbcTemplate.execute(dmlGenerator.generateInsertQuery(person));
    }
}
