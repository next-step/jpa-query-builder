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
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

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
    void find를_통해_찾을_때_이미_캐시된_데이터면_캐시_데이터를_반환한다() throws SQLException {
        Connection connection = server.getConnection();
        Map<Map.Entry<Class, Object>, Object> cache = new HashMap<>();
        EntityManager entityManager = new DefaultEntityManager(cache, connection);
        Person person = new Person(1L, "name", 3, "email", 0);

        cache.put(new AbstractMap.SimpleEntry<>(Person.class, person.getId()), person);

        Person found = entityManager.find(Person.class, person.getId());

        assertThat(found).isSameAs(person);
    }

    @Test
    void find를_통해_찾을_때_캐시가_안되어_있을_경우_디비에서_조회한다() throws SQLException {
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

        Person found = (Person) entityManager.persist(person);

        assertThat(found.getId()).isNotNull();
    }

    @Test
    void persist를_통해_저장할_때_아이디가_있으면_저장_후_반환한다() throws SQLException {
        Connection connection = server.getConnection();
        EntityManager entityManager = new DefaultEntityManager(connection);
        Person person = new Person(100L, "name", 3, "email", 0);

        Person found = (Person) entityManager.persist(person);

        assertThat(found.getId()).isEqualTo(person.getId());
    }

    @Test
    void persist를_통해_저장시_캐시에_저장된다() throws SQLException {
        Connection connection = server.getConnection();
        EntityManager entityManager = new DefaultEntityManager(connection);
        Person person = new Person(100L, "name", 3, "email", 0);

        Person saved = (Person) entityManager.persist(person);

        Person found = entityManager.find(Person.class, saved.getId());

        assertThat(found).isSameAs(saved);
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