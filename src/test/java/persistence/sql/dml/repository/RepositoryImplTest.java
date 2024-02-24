package persistence.sql.dml.repository;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.Person;
import persistence.sql.StandardDialectResolver;
import persistence.sql.ddl.query.builder.CreateQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.DialectResolutionInfo;
import persistence.sql.dml.query.builder.InsertQueryBuilder;
import persistence.sql.dml.query.builder.SelectQueryBuilder;
import persistence.sql.entity.EntityMappingTable;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RepositoryImplTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private static EntityMappingTable entityMappingTable;


    private Person person1;
    private Person person2;

    @BeforeAll
    static void setUpAll() throws SQLException {
        server = new H2();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @BeforeEach
    void setUp() throws SQLException {
        this.person1 = new Person(1L, "박재성", 10, "jason");
        this.person2 = new Person(2L, "이동규", 11, "cu");

        createTable();
        insertValue(person1);
        insertValue(person2);
    }

    private void createTable() throws SQLException {
        DialectResolutionInfo dialectResolutionInfo = new DialectResolutionInfo(server.getConnection().getMetaData());
        Dialect dialect = StandardDialectResolver.resolveDialect(dialectResolutionInfo);
        entityMappingTable = EntityMappingTable.from(Person.class);
        CreateQueryBuilder createQueryBuilder = CreateQueryBuilder.of(entityMappingTable, dialect.getTypeMapper(), dialect.getConstantTypeMapper());

        jdbcTemplate.execute(createQueryBuilder.toSql());
    }

    private static void insertValue(final Person person) {
        InsertQueryBuilder insertQueryBuilder = InsertQueryBuilder.from(person);

        jdbcTemplate.execute(insertQueryBuilder.toSql());
    }

    @Test
    void findAllTest() {
        Repository<Person> personRepository = new RepositoryImpl<>(jdbcTemplate, Person.class);

        List<Person> result = personRepository.findAll(SelectQueryBuilder.from(entityMappingTable).toSql());

        assertThat(result).isEqualTo(
                List.of(person1, person2)
        );
    }

}