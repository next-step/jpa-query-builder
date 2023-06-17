package persistence;

import database.DataSourceProperties;
import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.ddl.CreateTableBuilder;
import persistence.ddl.DeleteTableBuilder;
import persistence.ddl.InsertBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static final DataSourceProperties DEFAULT_PROPERTIES =
            new DataSourceProperties("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2(DEFAULT_PROPERTIES);
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            EntityReflectionManager entityReflectionManager = new EntityReflectionManager(Person.class);
            Table table = entityReflectionManager.table();
            Columns columns = entityReflectionManager.columns();

            CreateTableBuilder createTableBuilder = new CreateTableBuilder(table, columns);

            Person person = new Person("slow", 3, "slow@email.com");
            ColumnMap columnsMap = entityReflectionManager.columnValueMap(person);
            InsertBuilder insertBuilder = new InsertBuilder(entityReflectionManager.table(), columnsMap);


            DeleteTableBuilder deleteTableBuilder = new DeleteTableBuilder(table);

            jdbcTemplate.execute(createTableBuilder.query());
            jdbcTemplate.execute(insertBuilder.query());
            jdbcTemplate.execute(deleteTableBuilder.query());

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
