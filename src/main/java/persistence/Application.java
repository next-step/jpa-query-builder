package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.query.DdlQueryBuilder;
import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.Person;
import persistence.sql.dml.InsertQueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            //데이터 생성
            Person person = new Person();
            int AGE = 28;
            String NAME = "지영";
            String EMAIL = "jy@lim.com";
            person.setAge(AGE);
            person.setName(NAME);
            person.setEmail(EMAIL);

            //테이블 생성
            DdlQueryBuilder ddlQueryBuilder = DdlQueryBuilder.getInstance();
            EntityMetaData entityMetaData = new EntityMetaData(person);
            jdbcTemplate.execute(ddlQueryBuilder.createTable(entityMetaData));


            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
            jdbcTemplate.execute(insertQueryBuilder.create(entityMetaData));


            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
