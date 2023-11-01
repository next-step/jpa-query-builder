package persistence.entity;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.h2.jdbc.JdbcSQLNonTransientException;
import org.junit.jupiter.api.*;
import persistence.core.GenericRowMapper;
import persistence.dialect.Dialect;
import persistence.dialect.H2Dialect;
import persistence.sql.Query;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import java.sql.SQLException;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntityManagerImplTest {

    private Dialect dialect;
    private DatabaseServer databaseServer;
    private JdbcTemplate jdbcTemplate;
    private EntityManagerImpl entityManagerImpl;
    Person person = new Person().age(30).email("person@gmail.com").name("김쿼리");

    @BeforeAll
    void setup() throws SQLException {
        dialect = new H2Dialect();
        databaseServer = new H2();
        databaseServer.start();
        jdbcTemplate = new JdbcTemplate(databaseServer.getConnection());
        entityManagerImpl = new EntityManagerImpl(jdbcTemplate);

        create();
        insert(person);
    }

    @AfterAll
    void clean() {
        databaseServer.stop();
    }

    @Test
    @DisplayName("요구사항1 - find")
    void find() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(dialect);
        Query all = selectQueryBuilder.findAll(Person.class);
        GenericRowMapper<Person> personGenericRowMapper = new GenericRowMapper<>(Person.class);
        List<Person> PersonList = jdbcTemplate.query(all.getQuery().toString(), personGenericRowMapper);

        Person result = entityManagerImpl.find(Person.class, PersonList.get(0).getId());
        Assertions.assertThat(result).isEqualTo(PersonList.get(0));
    }

    @Test
    @DisplayName("요구사항2 - persist (insert)")
    void persist() {
        Person lee = new Person().age(30).email("person2@gmail.com").name("이쿼리");
        entityManagerImpl.persist(lee);

        Person result = entityManagerImpl.find(lee.getClass(), 2L);
        Assertions.assertThat(result).isEqualTo(lee);
    }

    @Test
    @DisplayName("요구사항3 - remove (delete)")
    void remove() {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(dialect);
        Query all = selectQueryBuilder.findAll(Person.class);
        GenericRowMapper<Person> personGenericRowMapper = new GenericRowMapper<>(Person.class);
        List<Person> query = jdbcTemplate.query(all.getQuery().toString(), personGenericRowMapper);
        Person removedPerson = entityManagerImpl.find(person.getClass(), 1L);
        entityManagerImpl.remove(removedPerson);

        Assertions.assertThatThrownBy(() -> entityManagerImpl.find(person.getClass(), 1L))
                .hasCauseInstanceOf(JdbcSQLNonTransientException.class);
    }

    private void create() {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(dialect);
        Query query = createQueryBuilder.queryForObject(new Person());
        jdbcTemplate.execute(query.getQuery().toString());
    }

    private void insert(Person person) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(dialect);
        Query query = insertQueryBuilder.queryForObject(person);
        jdbcTemplate.execute(query.getQuery().toString());
    }
}
