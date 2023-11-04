package persistence;

import database.DatabaseServer;
import database.H2;
import java.util.List;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DataDefinitionLanguageGenerator;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.DataManipulationLanguageGenerator;
import persistence.sql.dml.assembler.DataManipulationLanguageAssembler;
import persistence.sql.dml.insert.PersonRowMapper;
import persistence.sql.usecase.GetFieldFromClassUseCase;
import persistence.sql.usecase.GetFieldValueUseCase;
import persistence.sql.usecase.GetTableNameFromClassUseCase;
import persistence.sql.ddl.assembler.DataDefinitionLanguageAssembler;
import persistence.entity.Person;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            PersonRowMapper personRowMapper = new PersonRowMapper();
            String createQuery = generateCreateQuery();
            String dropQuery = generateDropQuery();
            String insertQuery = generateInsert();
            String selectQuery = generateFindAll();
            String selectByIdQuery = generateFindById();
            String deleteQuery = generateDeleteById();
            jdbcTemplate.execute(createQuery);
            jdbcTemplate.execute(insertQuery);
            List<Person> persons = jdbcTemplate.query(selectQuery, personRowMapper);
            for(Person p : persons) {
                System.out.println("###### Person All " + p);
            }
            List<Person> persons2 = jdbcTemplate.query(selectByIdQuery, personRowMapper);
            System.out.println("###### Person By Id " + persons2.get(0));

            jdbcTemplate.execute(deleteQuery);
            List<Person> personsEmpty = jdbcTemplate.query(selectQuery, personRowMapper);
            System.out.println("after delete size " + personsEmpty.size());

            jdbcTemplate.execute(dropQuery);
            System.out.println(createQuery);
            System.out.println(dropQuery);
            System.out.println(insertQuery);
            System.out.println(selectQuery);
            System.out.println(selectByIdQuery);
            System.out.println(deleteQuery);
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    public static String generateCreateQuery() {
        GetTableNameFromClassUseCase getTableNameFromClassUseCase = new GetTableNameFromClassUseCase();
        GetFieldFromClassUseCase getFieldFromClassUseCase = new GetFieldFromClassUseCase();
        DataDefinitionLanguageGenerator dataDefinitionLanguageGenerator = new DataDefinitionLanguageGenerator(
                getTableNameFromClassUseCase, getFieldFromClassUseCase
        );
        DataDefinitionLanguageAssembler dataDefinitionLanguageAssembler = new DataDefinitionLanguageAssembler(dataDefinitionLanguageGenerator);
        return dataDefinitionLanguageAssembler.assembleCreateTableQuery(Person.class);
    }

    public static String generateDropQuery() {
        GetTableNameFromClassUseCase getTableNameFromClassUseCase = new GetTableNameFromClassUseCase();
        GetFieldFromClassUseCase getFieldFromClass = new GetFieldFromClassUseCase();
        DataDefinitionLanguageGenerator dataDefinitionLanguageGenerator = new DataDefinitionLanguageGenerator(
                getTableNameFromClassUseCase, getFieldFromClass
        );
        DataDefinitionLanguageAssembler dataDefinitionLanguageAssembler = new DataDefinitionLanguageAssembler(dataDefinitionLanguageGenerator);
        return dataDefinitionLanguageAssembler.assembleDropTableQuery(Person.class);
    }

    private static String generateInsert() {
        DataManipulationLanguageAssembler dataManipulationLanguageAssembler = createDataManipulationLanguageAssembler();
        Person person = new Person("hello", 13, "gmail");
        return dataManipulationLanguageAssembler.generateInsert(person);
    }

    private static String generateFindAll() {
        DataManipulationLanguageAssembler dataManipulationLanguageAssembler = createDataManipulationLanguageAssembler();
        return dataManipulationLanguageAssembler.generateSelect(Person.class);
    }

    private static String generateFindById() {
        DataManipulationLanguageAssembler dataManipulationLanguageAssembler = createDataManipulationLanguageAssembler();
        return dataManipulationLanguageAssembler.generateSelectWithWhere(Person.class);
    }

    private static String generateDeleteById() {
        DataManipulationLanguageAssembler dataManipulationLanguageAssembler = createDataManipulationLanguageAssembler();
        return dataManipulationLanguageAssembler.generateDeleteWithWhere(Person.class);
    }

    private static DataManipulationLanguageAssembler createDataManipulationLanguageAssembler() {
        H2Dialect h2Dialect = new H2Dialect();
        GetTableNameFromClassUseCase getTableNameFromClassUseCase = new GetTableNameFromClassUseCase();
        GetFieldFromClassUseCase getFieldFromClassUseCase = new GetFieldFromClassUseCase();
        GetFieldValueUseCase getFieldValueUseCase = new GetFieldValueUseCase();
        DataManipulationLanguageGenerator dataManipulationLanguageGenerator = new DataManipulationLanguageGenerator(
                getTableNameFromClassUseCase,
                getFieldFromClassUseCase,
                getFieldValueUseCase);
        return new DataManipulationLanguageAssembler(
                h2Dialect, dataManipulationLanguageGenerator
        );
    }
}
