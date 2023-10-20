package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.core.DefaultPersistenceEnvironmentStrategy;
import persistence.core.EntityMetadata;
import persistence.core.EntityMetadataProvider;
import persistence.core.PersistenceEnvironment;
import persistence.dialect.H2Dialect;
import persistence.entity.EntityManager;
import persistence.entity.SimpleEntityManager;
import persistence.exception.PersistenceException;
import persistence.sql.ddl.DdlGenerator;
import persistence.sql.dml.DmlGenerator;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ApplicationTest {
    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;
    private PersistenceEnvironment persistenceEnvironment;
    private DdlGenerator ddlGenerator;
    private DmlGenerator dmlGenerator;
    private EntityMetadata<?> entityMetadata;
    private List<Person> people;

    @BeforeEach
    void setup() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        persistenceEnvironment = new PersistenceEnvironment(new DefaultPersistenceEnvironmentStrategy(server, new H2Dialect()));
        ddlGenerator = new DdlGenerator(persistenceEnvironment.getDialect());
        dmlGenerator = new DmlGenerator(persistenceEnvironment.getDialect());
        entityMetadata = EntityMetadataProvider.getInstance().getEntityMetadata(Person.class);
        final String createDdl = ddlGenerator.generateCreateDdl(entityMetadata);
        jdbcTemplate.execute(createDdl);
        people = createDummyUsers();
        people.forEach(person -> jdbcTemplate.execute(dmlGenerator.insert(person)));
    }

    @AfterEach
    void tearDown() {
        final String dropDdl = ddlGenerator.generateDropDdl(entityMetadata);
        jdbcTemplate.execute(dropDdl);
        server.stop();
    }

    @Test
    @DisplayName("쿼리 실행을 통해 데이터를 여러 row 를 넣어 정상적으로 나오는지 확인할 수 있다.")
    void findAllTest() {
        final List<Person> result =
                jdbcTemplate.query(dmlGenerator.findAll(Person.class), personRowMapper());

        assertThat(result).hasSize(people.size());
    }

    @Test
    @DisplayName("findById 를 통해 원하는 row 를 찾을 수 있다.")
    void findByIdTest() {
        final Person result =
                jdbcTemplate.queryForObject(dmlGenerator.findById(Person.class, 1L), personRowMapper());

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("entityManager.find 를 이용해 특정 객체를 DB 에서 조회할 수 있다.")
    void entityManagerFindTest() {
        final EntityManager entityManager = new SimpleEntityManager(persistenceEnvironment);

        final Person person = entityManager.find(Person.class, 1L);

        assertSoftly(softly -> {
            softly.assertThat(person).isNotNull();
            softly.assertThat(person.getId()).isEqualTo(1L);
            softly.assertThat(person.getName()).isEqualTo("test00");
            softly.assertThat(person.getAge()).isEqualTo(0);
            softly.assertThat(person.getEmail()).isEqualTo("test00@gmail.com");
        });
    }

    @Test
    @DisplayName("entityManager.persist 를 이용해 특정 객체를 DB 에 저장할 수 있다.")
    void entityManagerPersistTest() {
        final EntityManager entityManager = new SimpleEntityManager(persistenceEnvironment);
        final Person newPerson = new Person("min", 30, "jongmin4943@gmail.com");

        assertDoesNotThrow(() -> entityManager.persist(newPerson));
    }

    @Test
    @DisplayName("entityManager.remove 를 이용해 특정 객체를 DB 에서 삭제할 수 있다.")
    void entityManagerRemoveTest() {
        final EntityManager entityManager = new SimpleEntityManager(persistenceEnvironment);

        assertDoesNotThrow(() -> entityManager.remove(people.get(0)));
    }

    @Test
    @DisplayName("entityManager.close 를 이용해 DB Connection 을 닫을 수 있다.")
    void connectionCloseTest() {
        final EntityManager entityManager = new SimpleEntityManager(persistenceEnvironment);

        assertDoesNotThrow(entityManager::close);
    }

    @Test
    @DisplayName("entityManager.close 를 이용해 DB Connection 을 닫힌 후 에는 아무 작업을 할 수 없다.")
    void connectionAlreadyCloseTest() {
        final EntityManager entityManager = new SimpleEntityManager(persistenceEnvironment);

        entityManager.close();

        assertThatThrownBy(() -> {
            entityManager.find(Person.class, 1L);
            entityManager.persist(people.get(0));
            entityManager.remove(people.get(0));
        }).isInstanceOf(PersistenceException.class);
    }

    private RowMapper<Person> personRowMapper() {
        return rs -> new Person(rs.getLong("id"), rs.getString("nick_name"), rs.getInt("old"), rs.getString("email"));
    }

    private static List<Person> createDummyUsers() {
        final Person test00 = new Person("test00", 0, "test00@gmail.com");
        final Person test01 = new Person("test01", 10, "test01@gmail.com");
        final Person test02 = new Person("test02", 20, "test02@gmail.com");
        final Person test03 = new Person("test03", 30, "test03@gmail.com");
        return List.of(test00, test01, test02, test03);
    }
}
