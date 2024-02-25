package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.view.mysql.MySQLPrimaryKeyResolver;
import persistence.sql.dml.DmlQueryBuilder;
import persistence.sql.entity.Person;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EntityManagerImplTest {


    private static DatabaseServer server;
    private static EntityManager entityManager;


    @BeforeAll
    static void init() throws SQLException {
        server = new H2();
        server.start();
        DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(new MySQLPrimaryKeyResolver());
        Connection connection = server.getConnection();
        new JdbcTemplate(connection).execute(ddlQueryBuilder.createQuery(Person.class));
        entityManager = new EntityManagerImpl(connection, new DmlQueryBuilder());
    }
    @AfterAll
    static void destroy() {
        server.stop();
    }

    @Test
    @DisplayName("entity manager integration test")
    void persist() {
        Person person = new Person("cs", 29, "katd216@gmail.com", 0);

        entityManager.persist(person);

        Person foundPerson = entityManager.find(Person.class, 1l);

        assertThat(foundPerson).isNotNull();
    }
}