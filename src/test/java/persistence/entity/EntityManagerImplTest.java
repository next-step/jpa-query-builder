package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;
import persistence.sql.column.Columns;
import persistence.sql.column.IdColumn;
import persistence.sql.column.TableColumn;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dialect.Database;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.MysqlDialect;
import persistence.sql.dml.InsertQueryBuilder;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EntityManagerImplTest {

    private JdbcTemplate jdbcTemplate;
    private TableColumn table;
    private Columns columns;
    private IdColumn idColumn;
    private Dialect dialect;


    @BeforeEach
    void setUp() throws SQLException {
        DatabaseServer server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());

        Class<Person> personEntity = Person.class;
        table = new TableColumn(personEntity);
        dialect = new MysqlDialect();
        columns = new Columns(personEntity.getDeclaredFields(), dialect);
        idColumn = new IdColumn(personEntity.getDeclaredFields(), dialect);

        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(table, columns, idColumn);

        String createQuery = createQueryBuilder.build();
        jdbcTemplate.execute(createQuery);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(table);
        String dropQuery = dropQueryBuilder.build();
        jdbcTemplate.execute(dropQuery);
    }

    @DisplayName("find 메서드를 통해 id에 해당하는 Person 객체를 찾는다.")
    @Test
    void find() {
        // given
        EntityManager entityManager = new EntityManagerImpl(jdbcTemplate, new MysqlDialect());
        Long id = 1L;
        Person person = new Person(id, "John", 25, "qwer@asdf.com", 1);

        String insertQuery = new InsertQueryBuilder(dialect).build(person);
        jdbcTemplate.execute(insertQuery);

        // when
        Person findPerson = entityManager.find(Person.class, id);

        // then
        assertAll(
                () -> assertThat(person).isNotNull(),
                () -> assertThat(findPerson.getId()).isEqualTo(person.getId()),
                () -> assertThat(findPerson.getName()).isEqualTo(person.getName()),
                () -> assertThat(findPerson.getAge()).isEqualTo(person.getAge()),
                () -> assertThat(findPerson.getEmail()).isEqualTo(person.getEmail())
        );
    }

    @DisplayName("persist 메서드를 통해 Person 객체를 저장한다. - autho_increment인 경우 id가 1씩 증가한다.")
    @Test
    void persist() {
        // given
        EntityManager entityManager = new EntityManagerImpl(jdbcTemplate, new MysqlDialect());
        Person person = new Person("John", 99, "john@test.com", 1);

        // when
        entityManager.persist(person);

        // then
        Person findPerson = entityManager.find(Person.class, person.getId());
        assertAll(
                () -> assertThat(findPerson).isNotNull(),
                () -> assertThat(findPerson.getId()).isEqualTo(1L),
                () -> assertThat(findPerson.getName()).isEqualTo(person.getName()),
                () -> assertThat(findPerson.getAge()).isEqualTo(person.getAge()),
                () -> assertThat(findPerson.getEmail()).isEqualTo(person.getEmail())
        );
    }

    @DisplayName("persist 메서드를 통해 Person 객체를 저장한다. - id가 있다면 증가하지 않는다.")
    @Test
    void persistWhenHasId() {
        // given
        EntityManager entityManager = new EntityManagerImpl(jdbcTemplate, new MysqlDialect());
        Person person = new Person(1L, "John", 99, "john@test.com", 1);

        // when
        entityManager.persist(person);

        // then
        Person findPerson = entityManager.find(Person.class, person.getId());
        assertAll(
                () -> assertThat(findPerson).isNotNull(),
                () -> assertThat(findPerson.getId()).isEqualTo(person.getId()),
                () -> assertThat(findPerson.getName()).isEqualTo(person.getName()),
                () -> assertThat(findPerson.getAge()).isEqualTo(person.getAge()),
                () -> assertThat(findPerson.getEmail()).isEqualTo(person.getEmail())
        );
    }
}
