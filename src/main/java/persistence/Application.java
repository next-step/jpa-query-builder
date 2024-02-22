package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DatabaseDialect;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.DdlQueryBuilderFactory;
import persistence.sql.ddl.Person;
import persistence.sql.dml.DmlQueryBuilder;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            DdlQueryBuilderFactory factory = new DdlQueryBuilderFactory();

            DdlQueryBuilder ddlQueryBuilder = factory.getInstance(DatabaseDialect.MYSQL);

            jdbcTemplate.execute(ddlQueryBuilder.createQuery(Person.class));

            DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
            Person person = new Person("cs",29,"katd216@gmail.com",0);
            jdbcTemplate.execute(dmlQueryBuilder.insert(person));
            jdbcTemplate.execute(dmlQueryBuilder.insert(person));
            jdbcTemplate.execute(dmlQueryBuilder.insert(person));

            jdbcTemplate.query(dmlQueryBuilder.findAll(Person.class), printResultSet());
            System.out.println("###########");
            jdbcTemplate.query(dmlQueryBuilder.findById(Person.class,1l), printResultSet());

            jdbcTemplate.execute(ddlQueryBuilder.dropQuery(Person.class));

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static RowMapper<Object> printResultSet() {
        return resultSet -> {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                System.out.println(columnName + ": " + value);
            }
            System.out.println("-------------");
            return null;
        };
    }
}
