package persistence.sql;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.PersonMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.entity.Person;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private final PersonMapper personMapper = new PersonMapper();
    private final QueryBuilderFactory factory = new QueryBuilderFactory();

    @BeforeAll
    static void init() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @AfterAll
    static void destroy() {
        server.stop();
    }

    @Test
    @DisplayName("query builder integration test")
    void should_delete_entity() {
        QueryBuilder queryBuilder = factory.getInstance(DatabaseDialect.MYSQL);
        jdbcTemplate.execute(queryBuilder.createQuery(Person.class));
        Person person = new Person("cs", 29, "katd216@gmail.com", 0);
        jdbcTemplate.execute(queryBuilder.insert(person));
        jdbcTemplate.execute(queryBuilder.insert(person));
        List<Person> foundPerson = jdbcTemplate.query(queryBuilder.findById(Person.class, 1l), personMapper);
        jdbcTemplate.execute(queryBuilder.delete(foundPerson.get(0)));

        int totalSize = jdbcTemplate.query(queryBuilder.findAll(Person.class), personMapper).size();

        assertThat(totalSize).isEqualTo(1);
    }

}
