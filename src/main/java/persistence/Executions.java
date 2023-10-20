package persistence;

import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.Person;
import persistence.sql.Query;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;

import java.util.List;

public class Executions {
    private static final Logger logger = LoggerFactory.getLogger(Executions.class);
    private final JdbcTemplate jdbcTemplate;
    private final Query query;
    private final List<Class<?>> entities;

    public Executions(JdbcTemplate jdbcTemplate, Query query, List<Class<?>> entities) {
        this.jdbcTemplate = jdbcTemplate;
        this.query = query;
        this.entities = entities;
    }

    public void execute() {
        // create
        createEntities();

        // save
        saveEntities();

        // findAll

        // delete

        // drop
//        dropEntities();
    }

    private void createEntities() {
        for (Class<?> entity : entities) {
            CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(query, entity);
            jdbcTemplate.execute(createQueryBuilder.getQuery());
        }
    }

    private void saveEntities() {
        Person test1 = new Person("test1", 10, "test1@gmail.com", 0);
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(query, test1);
        jdbcTemplate.execute(insertQueryBuilder.getQuery());
    }

    private void dropEntities() {
        for (Class<?> entity : entities) {
            DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(query, entity);
            jdbcTemplate.execute(dropQueryBuilder.getQuery());
        }
    }

}
