package persistence.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static persistence.TestUtils.createDataDefinitionLanguageAssembler;
import static persistence.TestUtils.createDataManipulationLanguageAssembler;

import database.DatabaseServer;
import database.H2;
import java.sql.SQLException;
import jdbc.JdbcTemplate;
import jdbc.PersonRowMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.assembler.DataDefinitionLanguageAssembler;
import persistence.sql.dml.assembler.DataManipulationLanguageAssembler;


class EntityManagerTest {
    private final DataManipulationLanguageAssembler dataManipulationLanguageAssembler = createDataManipulationLanguageAssembler();
    private final DataDefinitionLanguageAssembler dataDefinitionLanguageAssembler = createDataDefinitionLanguageAssembler();
    private final PersonRowMapper personRowMapper = new PersonRowMapper();
    private DatabaseServer server;
    private JdbcTemplate jdbcTemplate;
    private EntityManager entityManager;


    @BeforeEach
    void setup() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
        entityManager = new EntityManagerImpl(personRowMapper, dataManipulationLanguageAssembler, jdbcTemplate);
        jdbcTemplate.execute(dataDefinitionLanguageAssembler.assembleCreateTableQuery(Person.class));
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(dataDefinitionLanguageAssembler.assembleDropTableQuery(Person.class));
        server.stop();
    }

    @Test
    void find() {
        Person person = new Person("tongnamuu", 11, "tongnamuu@naver.com");
        jdbcTemplate.execute(dataManipulationLanguageAssembler.generateInsert(person));

        Person findPerson = entityManager.find(Person.class, 1L);
        assertAll(
            () -> assertThat(findPerson.getName()).isEqualTo(person.getName()),
            () -> assertThat(findPerson.getAge()).isEqualTo(person.getAge()),
            () -> assertThat(findPerson.getEmail()).isEqualTo(person.getEmail())
        );
    }
}
