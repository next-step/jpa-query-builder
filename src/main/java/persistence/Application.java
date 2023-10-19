package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DataDefinitionLanguageGenerator;
import persistence.sql.ddl.GetFieldFromClass;
import persistence.sql.ddl.GetTableNameFromClass;
import persistence.sql.ddl.assembler.DataDefinitionLanguageAssembler;
import persistence.sql.ddl.cls.Person;

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
            jdbcTemplate.execute(createQuery);
            jdbcTemplate.execute(dropQuery);
            System.out.println(createQuery);
            System.out.println(dropQuery);
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    public static String generateCreateQuery() {
        GetTableNameFromClass getTableNameFromClass = new GetTableNameFromClass();
        GetFieldFromClass getFieldFromClass = new GetFieldFromClass();
        DataDefinitionLanguageGenerator dataDefinitionLanguageGenerator = new DataDefinitionLanguageGenerator(
            getTableNameFromClass, getFieldFromClass
        );
        DataDefinitionLanguageAssembler dataDefinitionLanguageAssembler = new DataDefinitionLanguageAssembler(dataDefinitionLanguageGenerator);
        return dataDefinitionLanguageAssembler.assembleCreateTableQuery(Person.class);
    }

    public static String generateDropQuery() {
        GetTableNameFromClass getTableNameFromClass = new GetTableNameFromClass();
        GetFieldFromClass getFieldFromClass = new GetFieldFromClass();
        DataDefinitionLanguageGenerator dataDefinitionLanguageGenerator = new DataDefinitionLanguageGenerator(
            getTableNameFromClass, getFieldFromClass
        );
        DataDefinitionLanguageAssembler dataDefinitionLanguageAssembler = new DataDefinitionLanguageAssembler(dataDefinitionLanguageGenerator);
        return dataDefinitionLanguageAssembler.assembleDropTableQuery(Person.class);
    }
}
