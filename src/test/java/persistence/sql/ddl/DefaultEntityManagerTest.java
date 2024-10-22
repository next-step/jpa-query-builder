package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;
import persistence.sql.ddl.generator.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultEntityManagerTest {
    private DatabaseServer server;

    private static void executeQuery(Connection connection, String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    @BeforeEach
    public void setUp() throws SQLException {
        Dialect dialect = new H2Dialect();
        EntityTable entityTable = EntityTable.from(Person.class);
        CreateDDLGenerator createDDLGenerator = new DefaultCreateDDLGenerator(dialect);

        String createQuery = createDDLGenerator.generate(entityTable);

        server = new H2();

        executeQuery(server.getConnection(), createQuery);

        server.start();
    }

    @Test
    void find를_디비에서_조회한다() throws SQLException {
        InsertDMLGenerator insertDMLGenerator = new DefaultInsertDMLGenerator();
        Connection connection = server.getConnection();
        EntityManager entityManager = new DefaultEntityManager(connection);
        Person person = new Person(1L, "name", 3, "email", 0);

        String query = insertDMLGenerator.generate(person);
        executeQuery(connection, query);

        Person found = entityManager.find(Person.class, person.getId());

        assertThat(found.getId()).isEqualTo(person.getId());
        assertThat(found.getName()).isEqualTo(person.getName());
        assertThat(found.getAge()).isEqualTo(person.getAge());
        assertThat(found.getEmail()).isEqualTo(person.getEmail());
        assertThat(found.getIndex()).isNull();
    }

    @Test
    void persist를_통해_저장할_때_아이디가_없으면_저장후_아이디를_설정하여_반환한다() throws SQLException {
        Connection connection = server.getConnection();
        EntityManager entityManager = new DefaultEntityManager(connection);
        Person person = new Person(null, "name", 3, "email", 0);

        Person found = entityManager.persist(person);

        assertThat(found.getId()).isNotNull();
    }

    @Test
    void persist를_통해_저장할_때_아이디가_있으면_저장_후_반환한다() throws SQLException {
        Connection connection = server.getConnection();
        EntityManager entityManager = new DefaultEntityManager(connection);
        Person person = new Person(100L, "name", 3, "email", 0);

        Person found = entityManager.persist(person);

        assertThat(found.getId()).isEqualTo(person.getId());
    }

    @Test
    void remove를_통해_삭제시_캐시에서_삭제_후_디비에서_삭제한다() throws SQLException {
        Connection connection = server.getConnection();
        EntityManager entityManager = new DefaultEntityManager(connection);
        Person person = new Person(100L, "name", 3, "email", 0);

        Person saved = (Person) entityManager.persist(person);

        entityManager.remove(saved);

        Person found = entityManager.find(Person.class, saved.getId());

        assertThat(found).isNull();
    }

    @Test
    void update를_할_경우_데이터가_수정된다() throws SQLException {
        Connection connection = server.getConnection();
        EntityManager entityManager = new DefaultEntityManager(connection);
        Person person = new Person(100L, "name", 3, "email", 0);

        Person saved = (Person) entityManager.persist(person);
        saved.setEmail("bobong");

        entityManager.persist(saved);

        Person found = entityManager.find(Person.class, saved.getId());

        assertThat(found.getEmail()).isEqualTo("bobong");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        EntityTable entityTable = EntityTable.from(Person.class);
        DropDDLGenerator dropDDLGenerator = new DefaultDropDDLGenerator();

        String dropQuery = dropDDLGenerator.generate(entityTable);

        executeQuery(server.getConnection(), dropQuery);

        server.stop();
    }
}