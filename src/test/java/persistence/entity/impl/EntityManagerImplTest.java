package persistence.entity.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import database.DatabaseServer;
import database.H2;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import persistence.entity.EntityManager;
import persistence.sql.QueryBuilder;
import persistence.sql.ddl.entity.Person;

class EntityManagerImplTest {
    private static DatabaseServer server;

    private EntityManager entityManager;

    private final static Map<Long, Person> idToPersonMap = Stream.of(
        new Person(1L, "root", 20, "root@gmail.com"),
        new Person(2L, "test", 30, "test@gmail.com"),
        new Person(3L, "user1", 40, "user1@gmail.com"),
        new Person(4L, "user2", 50, "user2@gmail.com")
    ).collect(Collectors.toMap(Person::getId, Function.identity()));

    @BeforeAll
    static void beforeAll() throws SQLException {
        server = new H2();
        server.start();
    }

    @AfterAll
    static void afterAll() {
        server.stop();
    }

    @BeforeEach
    void setUp() throws SQLException {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        entityManager = new EntityManagerImpl(jdbcTemplate);

        QueryBuilder queryBuilder = new QueryBuilder();

        jdbcTemplate.execute(queryBuilder.getCreateTableQuery(Person.class));
    }

    @AfterEach
    void tearDown() throws SQLException {
        dropTable();
    }

    private static JdbcTemplate getJdbcTemplate() throws SQLException {
        return new JdbcTemplate(server.getConnection());
    }

    private static Stream<Arguments> providePerson() {
        return idToPersonMap.values().stream()
            .map(Arguments::of);
    }

    @DisplayName("요구사항1 - find 메서드를 통해 id에 해당하는 Person 레코드를 조회할 수 있다.")
    @ParameterizedTest(name = "id: {0}")
    @ValueSource(longs = {1, 2, 3, 4})
    void find(Long id) throws SQLException {
        // given
        initializeTable();
        Person givenPerson = idToPersonMap.get(id);

        // when
        Person entity = entityManager.find(Person.class, id);

        // then
        assertAll(
            () -> assertThat(entity).isNotNull(),
            () -> assertThat(entity).isEqualTo(givenPerson)
        );
    }

    @DisplayName("요구사항2 - persist (insert) 메서드를 통해 Entity를 저장할 수 있다.")
    @Test
    void persist() {
        // given
        Person person = new Person("test", 20, "test@gmail.com");

        // when
        Object savedEntity = entityManager.persist(person);

        // then
        assertThat(savedEntity).isEqualTo(person);
    }

    @DisplayName("요구사항3 - remove (delete) 메서드를 통해 특정 Entity를 삭제할 수 있다.")
    @ParameterizedTest(name = "person: {0}")
    @MethodSource("providePerson")
    void remove(Person givenPerson) throws SQLException {
        // given
        initializeTable();
        Long id = givenPerson.getId();

        // when
        entityManager.remove(givenPerson);

        // then
        assertThatExceptionOfType(RuntimeException.class)
            .isThrownBy(() ->
                entityManager.find(Person.class, id)
            );

        Integer totalCountOfEntity = selectCountOfTable();
        assertThat(totalCountOfEntity).isEqualTo(idToPersonMap.size() - 1);
    }

    private void initializeTable() throws SQLException {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        QueryBuilder queryBuilder = new QueryBuilder();

        for (Person person : idToPersonMap.values()) {
            jdbcTemplate.execute(queryBuilder.getInsertQuery(person));
        }
    }

    private void dropTable() throws SQLException {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        QueryBuilder queryBuilder = new QueryBuilder();

        jdbcTemplate.execute(queryBuilder.getDropTableQuery(Person.class));
    }

    private Integer selectCountOfTable() throws SQLException {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        QueryBuilder queryBuilder = new QueryBuilder();

        return jdbcTemplate.queryForObject(queryBuilder.getSelectCountQuery(Person.class),
            resultSet -> resultSet.getInt(1));
    }
}
