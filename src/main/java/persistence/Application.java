package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.EntityFields;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.generator.*;

import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        CreateDDLGenerator createDDLGenerator = new DefaultCreateDDLGenerator();
        DropDDLGenerator dropDDLGenerator = new DefaultDropDDLGenerator();
        InsertDMLGenerator insertDMLGenerator = new DefaultInsertDMLGenerator();
        SelectDMLGenerator selectDMLGenerator = new DefaultSelectDMLGenerator();
        DeleteDMLGenerator deleteDMLGenerator = new DefaultDeleteDMLGenerator();

        EntityFields entityFields = EntityFields.from(Person.class);
        Person personForSave1 = new Person(null, "soora", 10, "soora@naver.com", 5);
        Person personForSave2 = new Person(null, "soora2", 12, "soora2@naver.com", 7);

        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            String createDDL = createDDLGenerator.generate(entityFields);

            jdbcTemplate.execute(createDDL);

            save(insertDMLGenerator, jdbcTemplate, personForSave1);
            save(insertDMLGenerator, jdbcTemplate, personForSave2);

            List<Person> persons = findAll(selectDMLGenerator, entityFields, jdbcTemplate);

            System.out.println("FINDALL----");
            persons.forEach(System.out::println);

            Person foundPerson = findById(selectDMLGenerator, entityFields, jdbcTemplate, persons.get(0).getId());

            System.out.println("FINDBYID----");
            System.out.println(foundPerson);

            System.out.println("DELETEBYID----");
            deleteById(deleteDMLGenerator, jdbcTemplate, entityFields, foundPerson.getId());

            List<Person> personsAfterDeleteById = findAll(selectDMLGenerator, entityFields, jdbcTemplate);

            System.out.println("FINDALL_AFTER_DELETEBYID----");
            personsAfterDeleteById.forEach(System.out::println);

            deleteAll(deleteDMLGenerator, jdbcTemplate, entityFields);

            List<Person> personsAfterDeleteAll = findAll(selectDMLGenerator, entityFields, jdbcTemplate);

            System.out.println("FINDALL_AFTER_DELETEALL----");
            personsAfterDeleteAll.forEach(System.out::println);

            String dropDDL = dropDDLGenerator.generate(entityFields);

            jdbcTemplate.execute(dropDDL);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void save(InsertDMLGenerator insertDMLGenerator, JdbcTemplate jdbcTemplate, Person person) {
        String query = insertDMLGenerator.generate(person);

        jdbcTemplate.execute(query);
    }

    private static void deleteById(DeleteDMLGenerator deleteDMLGenerator, JdbcTemplate jdbcTemplate, EntityFields entityFields, Long id) {
        String query = deleteDMLGenerator.generateDeleteById(entityFields, id);

        System.out.println(query);

        jdbcTemplate.execute(query);
    }

    private static void deleteAll(DeleteDMLGenerator deleteDMLGenerator, JdbcTemplate jdbcTemplate, EntityFields entityFields) {
        String query = deleteDMLGenerator.generateDeleteAll(entityFields);

        jdbcTemplate.execute(query);
    }

    private static List<Person> findAll(SelectDMLGenerator selectDMLGenerator, EntityFields entityFields, JdbcTemplate jdbcTemplate) {
        String query = selectDMLGenerator.generateFindAll(entityFields);

        return jdbcTemplate.query(query, toPerson());
    }

    private static Person findById(SelectDMLGenerator selectDMLGenerator, EntityFields entityFields, JdbcTemplate jdbcTemplate, Long id) {
        String query = selectDMLGenerator.generateFindById(entityFields, id);

        return jdbcTemplate.queryForObject(query, toPerson());
    }

    @NotNull
    private static RowMapper<Person> toPerson() {
        return resultSet -> {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("nick_name");
            Integer age = resultSet.getInt("old");
            String email = resultSet.getString("email");

            return new Person(id, name, age, email, null);
        };
    }
}

