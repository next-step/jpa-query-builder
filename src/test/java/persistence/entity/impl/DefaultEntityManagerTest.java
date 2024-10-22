package persistence.entity.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jdbc.JdbcTemplate;
import domain.Person;
import database.H2;
import persistence.sql.ddl.CreateTableQueryBuilder;
import persistence.sql.ddl.DropTableQueryBuilder;
import persistence.sql.ddl.QueryBuilder;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;


public class DefaultEntityManagerTest {
    private DefaultEntityManager entityManager;
    private JdbcTemplate jdbcTemplate;
    private Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        H2 databaseServer = new H2();
        databaseServer.start();
        connection = databaseServer.getConnection();
        jdbcTemplate = new JdbcTemplate(connection);
        entityManager = new DefaultEntityManager(jdbcTemplate);

        // Create table for Person
        QueryBuilder ddlQueryBuilder = new CreateTableQueryBuilder(Person.class);
        String createTableQuery = ddlQueryBuilder.executeQuery();
        jdbcTemplate.execute(createTableQuery);
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Drop table for Person
        QueryBuilder ddlQueryBuilder = new DropTableQueryBuilder(Person.class);
        String dropTableQuery = ddlQueryBuilder.executeQuery();
        jdbcTemplate.execute(dropTableQuery);
        connection.close();
    }

    @Test
    public void testPersist() {
        Person person = Person.of(null, "John", 25, "john@example.com", 1);
        Person persistedPerson = (Person) entityManager.persist(person);
        assertNotNull(persistedPerson);
    }

    @Test
    public void testFind() {
        Person person = Person.of(null, "John", 25, "john@example.com", 1);
        entityManager.persist(person);
        Person foundPerson = entityManager.find(Person.class, 1L);
        assertNotNull(foundPerson);
    }

    @Test
    public void testRemove() {
        Person person = Person.of(null, "John", 25, "john@example.com", 1);
        entityManager.persist(person);
        entityManager.remove(Person.class, 1L);
        assertNull(entityManager.find(Person.class, 1L));
    }

    @Test
    public void testUpdate() {
        Person person = Person.of(null, "John", 25, "john@example.com", 1);
        entityManager.persist(person);
        Person updatedPerson = Person.of(1L, "John Updated", 26, "john.updated@example.com", 1);
        entityManager.update(updatedPerson);
        Person foundPerson = entityManager.find(Person.class, 1L);
        assertNotNull(foundPerson);
    }
}