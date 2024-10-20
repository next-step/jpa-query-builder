package persistence.entity;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.DialectFactory;
import persistence.sql.dml.DmlQueryBuilder;
import persistence.fixture.PersonWithTransientAnnotation;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntityManagerTest {
    DatabaseServer databaseServer;

    Dialect dialect;

    DmlQueryBuilder dmlQueryBuilder;

    DdlQueryBuilder ddlQueryBuilder;

    JdbcTemplate jdbcTemplate;

    EntityManager entityManager;

    @BeforeEach
    void setup() throws SQLException {
        databaseServer = new H2();
        dialect = DialectFactory.create(databaseServer.getClass());
        dmlQueryBuilder = new DmlQueryBuilder(dialect);
        ddlQueryBuilder = new DdlQueryBuilder(dialect);
        jdbcTemplate = new JdbcTemplate(databaseServer.getConnection());
        entityManager = new EntityManagerImpl(dmlQueryBuilder, jdbcTemplate);

        jdbcTemplate.execute(ddlQueryBuilder.buildCreateTableQuery(PersonWithTransientAnnotation.class));
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(ddlQueryBuilder.buildDropTableQuery(PersonWithTransientAnnotation.class));
        databaseServer.stop();
    }

    @Nested
    @DisplayName("findById 테스트")
    class FindByIdTest {
        @Test
        @DisplayName("Long 타입 id에 해당하는 엔티티를 구한다.")
        void succeedToFindById() {
            // given
            PersonWithTransientAnnotation person = new PersonWithTransientAnnotation(
                    1L, "홍길동", 20, "test@test.com", 1
            );
            jdbcTemplate.execute(dmlQueryBuilder.buildInsertQuery(person));

            // when
            PersonWithTransientAnnotation personFound = entityManager.findById(PersonWithTransientAnnotation.class, 1L);

            // then
            assertEquals(1L, personFound.getId());
        }

        @Test
        @DisplayName("해당하는 엔티티가 없다면 에러를 내뱉는다.")
        void failToFindById() {
            assertThrows(RuntimeException.class, () -> {
                entityManager.findById(PersonWithTransientAnnotation.class, 1L);
            });
        }
    }

    @Nested
    @DisplayName("persist 테스트")
    class PersistTest {
        @Test
        @DisplayName("주어진 엔티티를 디비에 저장한다.")
        void succeedToPersist() {
            // given
            PersonWithTransientAnnotation person = new PersonWithTransientAnnotation(
                    1L, "홍길동", 20, "test@test.com", 1
            );
            entityManager.persist(person);

            // when
            PersonWithTransientAnnotation foundPerson = entityManager.findById(
                    PersonWithTransientAnnotation.class,
                    1L
            );

            // then
            assertEquals(foundPerson.getName(), person.getName());
        }
    }
}
