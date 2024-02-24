package persistence.sql;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.PersonMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DdlQueryBuild;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.view.mysql.MySQLPrimaryKeyResolver;
import persistence.sql.dml.DmlQueryBuilder;
import persistence.sql.entity.Person;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private final PersonMapper personMapper = new PersonMapper();
    private final DdlQueryBuild ddlQueryBuilder = new DdlQueryBuilder(new MySQLPrimaryKeyResolver());
    private final DmlQueryBuilder dmlQueryBuild = new DmlQueryBuilder();

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
        jdbcTemplate.execute(ddlQueryBuilder.createQuery(Person.class));
        Person person = new Person("cs", 29, "katd216@gmail.com", 0);

        jdbcTemplate.execute(dmlQueryBuild.insert(person));
        jdbcTemplate.execute(dmlQueryBuild.insert(person));
        List<Person> foundPerson = jdbcTemplate.query(dmlQueryBuild.findById(Person.class, 1l), personMapper);
        jdbcTemplate.execute(dmlQueryBuild.delete(foundPerson.get(0)));

        int totalSize = jdbcTemplate.query(dmlQueryBuild.findAll(Person.class), personMapper).size();

        assertThat(totalSize).isEqualTo(1);
    }

}
