package persistence.sql.dml;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import persistence.sql.QueryBuilder;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dialect.h2.H2Dialect;

@DisplayName("InsertQueryBuilder 통합 테스트")
class InsertQueryBuilderIntegrationTest {

    private DatabaseServer server;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());

        QueryBuilder createQueryBuilder = CreateQueryBuilder.from(H2Dialect.getInstance());
        jdbcTemplate.execute(createQueryBuilder.generateQuery(Person.class));
    }

    @AfterEach
    void tearDown() {
        QueryBuilder dropQueryBuilder = DropQueryBuilder.from();
        jdbcTemplate.execute(dropQueryBuilder.generateQuery(Person.class));
        server.stop();
    }

    @DisplayName("InsertQueryBuilder는 Insert 쿼리를 생성한다.")
    @CsvSource({
        "user1, 1, test1@abc.com, 1",
        "user2, 2, test2@abc.com, 2",
        "user3, 3, test3@abc.com, 3",
        "user4, 4, test4@abc.com, 4"
    })
    @ParameterizedTest
    void canInsertRowByEntityObject(String name, int age, String email, int index) throws SQLException {
        //given
        QueryBuilder queryBuilder = InsertQueryBuilder.from();

        Person person = Person.of(name, age, email, index);

        String insetQuery = queryBuilder.generateQuery(person);

        System.out.println(insetQuery);

        jdbcTemplate.execute(insetQuery);
    }
}
