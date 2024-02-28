package database;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.sql.ddl.DDLQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.DMLQueryBuilder;
import persistence.sql.model.Table;
import persistence.study.sql.ddl.Person3;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

class SimpleDatabaseTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private static Database database;

    @BeforeAll
    static void initialize() throws SQLException {
        server = new H2();
        server.start();

        Connection jdbcConnection = server.getConnection();
        jdbcTemplate = new JdbcTemplate(jdbcConnection);
        database = new SimpleDatabase(jdbcTemplate);
    }

    @AfterAll
    static void close() {
        server.stop();
    }

    @BeforeEach
    void beforeEach() {
        Dialect dialect = new H2Dialect();
        Table table = new Table(Person3.class);

        DDLQueryBuilder ddlQueryBuilder = new DDLQueryBuilder(table, dialect);
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table);

        String createQuery = ddlQueryBuilder.buildCreateQuery();
        String insertQuery = dmlQueryBuilder.buildInsertQuery(new Person3(null, "qwer", 123, "e@email.com"));

        jdbcTemplate.execute(createQuery);
        jdbcTemplate.execute(insertQuery);
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.execute("DROP TABLE users");
    }

    @Test
    @DisplayName("정상적인 쿼리를 넣었을 때 실행되는지 확인")
    void executeQuery() {
        assertThatCode(() -> database.execute("INSERT INTO users (id,nick_name,old,email) VALUES (null,'a',1,'e@email.com')"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("정상적이지 않은 쿼리를 넣었을 때 exception을 던진다.")
    void executeQueryWithWrongQuery() {
        assertThatThrownBy(() -> database.execute("efawf"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("정상적으로 엔티티에 매핑되는지 확인")
    void executeQueryForObject() {
        Person3 result = database.executeQueryForObject(Person3.class, "SELECT * FROM users WHERE id=1");

        Person3 expect = new Person3(1L, "qwer", 123, "e@email.com");
        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("정상적이지 않은 쿼리를 넣었을 때 exception을 던진다.")
    void executeQueryForObjectWithWrongQuery() {
        assertThatThrownBy(() -> database.executeQueryForObject(Person3.class, "qwefwef"))
                .isInstanceOf(RuntimeException.class);
    }
}
