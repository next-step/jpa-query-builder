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
            String createQuery = generateCreateQuery();
            String dropQuery = generateDropQuery();
            String insertQuery = generateInsert();
            String selectQuery = generateFindAll();
            jdbcTemplate.execute(createQuery);
            jdbcTemplate.execute(insertQuery);
            List<Person> query = jdbcTemplate.query(selectQuery, (resultSet -> new Person(resultSet.getString("nick_name"), resultSet.getInt("old"), resultSet.getString("email"))));
            for(Person p : query) {
                System.out.println("###### Person " + p);
            }
            jdbcTemplate.execute(dropQuery);
            System.out.println(createQuery);
            System.out.println(dropQuery);
            System.out.println(insertQuery);
            System.out.println(selectQuery);
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
        H2Dialect h2Dialect = new H2Dialect();
        GetTableNameFromClassUseCase getTableNameFromClassUseCase = new GetTableNameFromClassUseCase();
        GetFieldFromClassUseCase getFieldFromClassUseCase = new GetFieldFromClassUseCase();
        GetFieldValueUseCase getFieldValueUseCase = new GetFieldValueUseCase();
        DataManipulationLanguageGenerator dataManipulationLanguageGenerator = new DataManipulationLanguageGenerator(
            getTableNameFromClassUseCase,
            getFieldFromClassUseCase,
            getFieldValueUseCase);
        DataManipulationLanguageAssembler dataManipulationLanguageAssembler = new DataManipulationLanguageAssembler(
            h2Dialect, dataManipulationLanguageGenerator
        );
        Person person = new Person("hello", 13, "gmail");
        return dataManipulationLanguageAssembler.generateInsert(person);
    }

    private static String generateFindAll() {
        H2Dialect h2Dialect = new H2Dialect();
        GetTableNameFromClassUseCase getTableNameFromClassUseCase = new GetTableNameFromClassUseCase();
        GetFieldFromClassUseCase getFieldFromClassUseCase = new GetFieldFromClassUseCase();
        GetFieldValueUseCase getFieldValueUseCase = new GetFieldValueUseCase();
        DataManipulationLanguageGenerator dataManipulationLanguageGenerator = new DataManipulationLanguageGenerator(
            getTableNameFromClassUseCase,
            getFieldFromClassUseCase,
            getFieldValueUseCase);
        DataManipulationLanguageAssembler dataManipulationLanguageAssembler = new DataManipulationLanguageAssembler(
            h2Dialect, dataManipulationLanguageGenerator
        );
        return dataManipulationLanguageAssembler.generateSelect(Person.class);
    }
}
