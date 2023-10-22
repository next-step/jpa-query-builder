package persistence.entity;

import entity.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.DatabaseTest;
import persistence.entitiy.EntityManagerImpl;
import persistence.entitiy.attribute.EntityAttribute;
import persistence.entitiy.context.PersistencContext;
import persistence.fixture.TestEntityFixture;
import persistence.persister.EntityPersister;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.infra.H2SqlConverter;
import persistence.sql.parser.AttributeParser;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("EntityManager 클래스의")
public class EntityManagerTest extends DatabaseTest {

    @Nested
    @DisplayName("findById 메소드는")
    public class findById {

        @Nested
        @DisplayName("Person 클래스와 아이디가 주어졌을떄")
        public class withPerson {
            @Test
            @DisplayName("적절한 Person 객체를 반환한다.")
            void returnObject() throws SQLException {
                setUpFixtureTable(Person.class, new H2SqlConverter());

                EntityAttribute entityAttribute = EntityAttribute.of(Person.class, new AttributeParser());
                JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
                Person person = new Person(1L, "민준", 29, "minjoon.com");

                String insertDML
                        = new InsertQueryBuilder().prepareStatement(entityAttribute.createEntityContext(person));
                jdbcTemplate.execute(insertDML);

                PersistencContext persistencContext = PersistencContext.getInstance();
                EntityPersister entityPersister = new EntityPersister(jdbcTemplate);
                EntityManagerImpl entityManager = EntityManagerImpl.of(persistencContext, entityPersister);

                Person retrieved = entityManager.findById(Person.class, "1");
                assertThat(retrieved.toString()).isEqualTo("Person{id=1, name='민준', age=29, email='minjoon.com'}");
            }
        }

        @Nested
        @DisplayName("SampleOneWithValidAnnotation 클래스와 아이디가 주어졌을떄")
        public class withSampleOneWithValidAnnotation {
            @Test
            @DisplayName("적절한 SampleOneWithValidAnnotation 객체를 반환한다.")
            void returnObject() throws SQLException {
                setUpFixtureTable(TestEntityFixture.SampleOneWithValidAnnotation.class, new H2SqlConverter());

                EntityAttribute entityAttribute =
                        EntityAttribute.of(TestEntityFixture.SampleOneWithValidAnnotation.class, new AttributeParser());
                JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
                TestEntityFixture.SampleOneWithValidAnnotation sample =
                        new TestEntityFixture.SampleOneWithValidAnnotation(1L, "민준", 29);

                String insertDML
                        = new InsertQueryBuilder().prepareStatement(entityAttribute.createEntityContext(sample));
                jdbcTemplate.execute(insertDML);

                PersistencContext persistencContext = PersistencContext.getInstance();
                EntityPersister entityPersister = new EntityPersister(jdbcTemplate);
                EntityManagerImpl entityManager = EntityManagerImpl.of(persistencContext, entityPersister);

                TestEntityFixture.SampleOneWithValidAnnotation retrieved =
                        entityManager.findById(TestEntityFixture.SampleOneWithValidAnnotation.class, "1");

                assertThat(retrieved.toString()).isEqualTo("SampleOneWithValidAnnotation{id=1, name='민준', age=29}");
            }
        }

        @Nested
        @DisplayName("SampleTwoWithValidAnnotation 클래스와 아이디가 주어졌을떄")
        public class withSampleTwoWithValidAnnotation {
            @Test
            @DisplayName("적절한 SampleTwoWithValidAnnotation 객체를 반환한다.")
            void returnObject() throws SQLException {
                setUpFixtureTable(TestEntityFixture.SampleTwoWithValidAnnotation.class, new H2SqlConverter());

                EntityAttribute entityAttribute =
                        EntityAttribute.of(TestEntityFixture.SampleTwoWithValidAnnotation.class, new AttributeParser());
                JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
                TestEntityFixture.SampleTwoWithValidAnnotation sample =
                        new TestEntityFixture.SampleTwoWithValidAnnotation(1L, "민준", 29);

                String insertDML
                        = new InsertQueryBuilder().prepareStatement(entityAttribute.createEntityContext(sample));
                jdbcTemplate.execute(insertDML);

                PersistencContext persistencContext = PersistencContext.getInstance();
                EntityPersister entityPersister = new EntityPersister(jdbcTemplate);
                EntityManagerImpl entityManager = EntityManagerImpl.of(persistencContext, entityPersister);

                TestEntityFixture.SampleTwoWithValidAnnotation retrieved =
                        entityManager.findById(TestEntityFixture.SampleTwoWithValidAnnotation.class, "1");

                assertThat(retrieved.toString()).isEqualTo("SampleTwoWithValidAnnotation{id=1, name='민준', age=29}");
            }
        }
    }
}
