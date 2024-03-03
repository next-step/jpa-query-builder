package persistence.entity;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.model.Table;
import persistence.sql.ddl.converter.H2TypeConverter;
import persistence.sql.ddl.mapping.DDLQueryBuilder;
import persistence.sql.ddl.mapping.H2PrimaryKeyGenerationType;
import persistence.sql.ddl.mapping.QueryBuilder;
import persistence.sql.ddl.model.DDLColumn;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.model.DMLColumn;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntityManagerImplTest {

    private DatabaseServer server;
    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    private QueryBuilder queryBuilder;
    private Person expected;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();
        expected = new Person(1L, "name", 10, "a@a.com", 1);

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        entityManager = new EntityManagerImpl(jdbcTemplate);
        queryBuilder = new DDLQueryBuilder(
                new Table(expected.getClass()),
                new DDLColumn(new H2TypeConverter(), new H2PrimaryKeyGenerationType())
        );

        final String createQuery = queryBuilder.create(Person.class);
        jdbcTemplate.execute(createQuery);
    }

    @AfterEach
    void tearDown() {
        final String dropQuery = queryBuilder.drop(Person.class);
        jdbcTemplate.execute(dropQuery);

        server.stop();
    }

    @Test
    @DisplayName("Entity 를 정상적으로 조회한다.")
    void findTest() {
        insertDummyPerson();

        final Person actual = entityManager.find(Person.class, 1L);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Entity 를 정상적으로 저장한다.")
    void persistTest() {
        entityManager.persist(expected);

        final Person actual = entityManager.find(Person.class, 1L);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Entity 를 제거한 후 조회하면 RuntimeException 예외가 발생한다.")
    void removeTest() {
        insertDummyPerson();

        entityManager.remove(expected);

        assertThrows(RuntimeException.class, () -> entityManager.find(Person.class, 1L));
    }

    private void insertDummyPerson() {
        final InsertQueryBuilder queryBuilder = new InsertQueryBuilder(
                new Table(expected.getClass()),
                new DMLColumn(expected)
        );
        final String insertQuery = queryBuilder.build();

        jdbcTemplate.execute(insertQuery);
    }

}
