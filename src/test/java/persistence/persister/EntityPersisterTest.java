package persistence.persister;

import jdbc.JdbcTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.DatabaseTest;
import persistence.fixture.TestEntityFixture;
import persistence.sql.infra.H2SqlConverter;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("EntityPersister 클래스의")
public class EntityPersisterTest extends DatabaseTest {
    TestEntityFixture.SampleOneWithValidAnnotation sample
            = new TestEntityFixture.SampleOneWithValidAnnotation(1, "test_nick_name", 29);

    @Nested
    @DisplayName("insert 메소드는")
    class insert {
        @Nested
        @DisplayName("적절한 인스턴스가 주어지면")
        public class withInstance {
            @Test
            @DisplayName("객체를 데이터베이스에 저장하고, 아이디가 매핑된 객체를 반환한다.")
            void returnInstanceWithIdMapping() throws SQLException {
                setUpFixtureTable(TestEntityFixture.SampleOneWithValidAnnotation.class, new H2SqlConverter());
                JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
                EntityPersister entityPersister = new EntityPersister(jdbcTemplate);
                TestEntityFixture.SampleOneWithValidAnnotation inserted = entityPersister.insert(sample);
                assertThat(inserted.toString())
                        .isEqualTo("SampleOneWithValidAnnotation{id=1, name='test_nick_name', age=29}");
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class findById {
        @Nested
        @DisplayName("클래스정보와 아이디가 주어지면")
        public class withInstance {
            @Test
            @DisplayName("객체를 찾아온다.")
            void returnData() throws SQLException {
                setUpFixtureTable(TestEntityFixture.SampleOneWithValidAnnotation.class, new H2SqlConverter());
                JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
                EntityPersister entityPersister = new EntityPersister(jdbcTemplate);
                TestEntityFixture.SampleOneWithValidAnnotation inserted = entityPersister.insert(sample);

                TestEntityFixture.SampleOneWithValidAnnotation retrieved =
                        entityPersister.findById(TestEntityFixture.SampleOneWithValidAnnotation.class, inserted.getId().toString());

                assertThat(retrieved.toString())
                        .isEqualTo("SampleOneWithValidAnnotation{id=1, name='test_nick_name', age=29}");
            }
        }
    }
}
