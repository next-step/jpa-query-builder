package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.DialectFactory;
import persistence.sql.dml.DmlQueryBuilder;
import persistence.sql.fixture.PersonWithTransientAnnotation;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityManagerTest {
    @Test
    @DisplayName("Long 타입 id에 해당하는 엔티티를 구한다.")
    void testFind() throws SQLException {
        DatabaseServer databaseServer = new H2();
        Dialect dialect = DialectFactory.create(databaseServer.getClass());
        DmlQueryBuilder queryBuilder = new DmlQueryBuilder(dialect);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(databaseServer.getConnection());

        EntityManager entityManager = new EntityManagerImpl(queryBuilder, jdbcTemplate);

        // given
        PersonWithTransientAnnotation person = new PersonWithTransientAnnotation(
                1L, "홍길동", 20, "test@test.com", 1
        );
        jdbcTemplate.execute(queryBuilder.buildInsertQuery(person));

        // when
        PersonWithTransientAnnotation personFound = entityManager.find(PersonWithTransientAnnotation.class, 1L);

        // then
        assertEquals(1L, personFound.getId());
    }
}
