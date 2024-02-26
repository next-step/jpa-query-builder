package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.Person;
import persistence.sql.ddl.DDLQueryGenerator;
import persistence.sql.dialect.H2Dialect;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class DMLQueryGeneratorH2DbTest {
    private static JdbcTemplate jdbcTemplate;
    private static DatabaseServer server;
    private DDLQueryGenerator ddlQueryGenerator;
    private final DMLQueryGenerator dmlQueryGenerator = new DMLQueryGenerator(Person.class);

    @BeforeAll
    public static void tearUp() throws Exception {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @AfterAll
    public static void tearDown() {
        server.stop();
    }

    @BeforeEach
    public void setUp() {
        ddlQueryGenerator = new DDLQueryGenerator(new H2Dialect(), Person.class);
        String sql = ddlQueryGenerator.generateCreateQuery();
        jdbcTemplate.execute(sql);
    }

    @AfterEach
    public void cleanUp() {
        jdbcTemplate.execute(ddlQueryGenerator.generateDropTableQuery());
    }

    @Test
    @DisplayName("h2db에 insert 하고 select 로 확인")
    void testInsert() {
        String nickName1 = "person1";
        String nickName2 = "person2";
        int age = 20;
        String email = "email";
        Person person1 = new Person(null, nickName1, age, email, null);
        Person person2 = new Person(null, nickName2, age, email, null);

        jdbcTemplate.execute(dmlQueryGenerator.generateInsertQuery(person1));
        jdbcTemplate.execute(dmlQueryGenerator.generateInsertQuery(person2));

        List<Person> persons = jdbcTemplate.query(
                dmlQueryGenerator.generateSelectQuery(new WhereBuilder()),
                rs -> new Person(
                        rs.getLong("id"),
                        rs.getString("nick_name"),
                        rs.getInt("old"),
                        rs.getString("email"),
                        null
                )
        );

        assertSoftly(softly -> {
            softly.assertThat(persons).hasSize(2);
            softly.assertThat(persons.get(0).getName()).isEqualTo(nickName1);
            softly.assertThat(persons.get(1).getName()).isEqualTo(nickName2);
        });
    }
}
