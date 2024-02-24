package persistence.sql.dml.repository;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.StandardDialectResolver;
import persistence.sql.ddl.query.builder.CreateQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.DialectResolutionInfo;
import persistence.sql.entity.EntityMappingTable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryImplTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private static EntityMappingTable entityMappingTable;


    private static Person person1;
    private static Person person2;

    @BeforeAll
    static void setUpAll() throws SQLException {
        server = new H2();
        jdbcTemplate = new JdbcTemplate(server.getConnection());

        person1 = new Person(1L, "박재성", 10, "jason");
        person2 = new Person(2L, "이동규", 11, "cu");

        createTable();
    }

    @BeforeEach
    void setUp() {
        Repository<Person> personRepository = new RepositoryImpl<>(jdbcTemplate, Person.class);
        personRepository.deleteAll();

        personRepository.save(person1);
        personRepository.save(person2);
    }

    private static void createTable() throws SQLException {
        DialectResolutionInfo dialectResolutionInfo = new DialectResolutionInfo(server.getConnection().getMetaData());
        Dialect dialect = StandardDialectResolver.resolveDialect(dialectResolutionInfo);
        entityMappingTable = EntityMappingTable.from(Person.class);
        CreateQueryBuilder createQueryBuilder = CreateQueryBuilder.of(entityMappingTable, dialect.getTypeMapper(), dialect.getConstantTypeMapper());

        jdbcTemplate.execute(createQueryBuilder.toSql());
    }

    @DisplayName("전체 목록을 반환한다.")
    @Test
    void findAllTest() {
        Repository<Person> personRepository = new RepositoryImpl<>(jdbcTemplate, Person.class);

        List<Person> result = personRepository.findAll();

        assertThat(result).isEqualTo(
                List.of(person1, person2)
        );
    }

    @DisplayName("아이디값에 해당하는 값을 반환한다.")
    @Test
    void findByIdTest() {
        Repository<Person> personRepository = new RepositoryImpl<>(jdbcTemplate, Person.class);

        Optional<Person> person = personRepository.findById(1L);

        assertThat(person.get()).isEqualTo(person1);
    }

    @DisplayName("해당하는 아이디에 해당하는 Person 값을 삭제한다.")
    @Test
    void deleteIdTest() {
        Repository<Person> personRepository = new RepositoryImpl<>(jdbcTemplate, Person.class);

        personRepository.deleteById(1L);

        Optional<Person> person = personRepository.findById(1L);

        assertThat(person.isPresent()).isFalse();
    }

}