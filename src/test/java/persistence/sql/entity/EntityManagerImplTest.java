package persistence.sql.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.dml.QueryBuilder;
import persistence.sql.dml.domain.Person;
import persistence.sql.dml.keygenerator.H2KeyGenerator;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EntityManagerImplTest {
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() throws SQLException {
        final DatabaseServer databaseServer = new H2();
        final Connection connection = databaseServer.getConnection();

        jdbcTemplate = new JdbcTemplate(connection);
        final DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder();
        final String createSql = ddlQueryBuilder.createDdl(Person.class);
        jdbcTemplate.execute(createSql);
    }

    @DisplayName("EntityManagerImpl find를 호출하면 엔티티가 리턴된다.")
    @Test
    void findTest() {
        final Person person = new Person("simpson", 31, "simpson@naver.com");
        final QueryBuilder queryBuilder = new QueryBuilder(person, new H2KeyGenerator());
        jdbcTemplate.execute(queryBuilder.createInsertQuery());

        final EntityManager entityManager = new EntityManagerImpl();
        final Person findPerson = entityManager.find(Person.class, 1L);

        assertThat(findPerson).isEqualTo(person);
    }
}