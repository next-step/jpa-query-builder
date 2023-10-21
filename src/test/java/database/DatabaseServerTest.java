package database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.DatabaseTest;
import persistence.fixture.TestEntityFixture;
import persistence.mapper.TestEntityRowMapper;
import persistence.sql.attribute.EntityAttribute;
import persistence.sql.common.DDLType;
import persistence.sql.ddl.builder.DDLQueryBuilder;
import persistence.sql.ddl.builder.DDLQueryBuilderFactory;
import persistence.sql.ddl.converter.SqlConverter;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.infra.H2SqlConverter;
import persistence.sql.parser.AttributeParser;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("DatabaseServer 클래스의")
public class DatabaseServerTest extends DatabaseTest {
    DDLQueryBuilder queryBuilder = DDLQueryBuilderFactory.createQueryBuilder(DDLType.CREATE);
    SqlConverter sqlConverter = new H2SqlConverter();
    AttributeParser parser = new AttributeParser();
    EntityAttribute entityAttribute = EntityAttribute.of(TestEntityFixture.EntityWithValidAnnotation.class, parser);

    private void setUpFixtureTable() {
        jdbcTemplate.execute(queryBuilder.prepareStatement(entityAttribute, sqlConverter));
    }

    @Nested
    @DisplayName("executeQuery 메소드는")
    class executeQuery {
        @Nested
        @DisplayName("유효한 데이터가 주어지면")
        class withValidArgs {
            @Test
            @DisplayName("예외를 던지지않고 종료한다.")
            void doseNotThrowException() {
                setUpFixtureTable();

                InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();

                TestEntityFixture.EntityWithValidAnnotation entityOne =
                        new TestEntityFixture.EntityWithValidAnnotation("민준", 29);
                TestEntityFixture.EntityWithValidAnnotation entityTwo =
                        new TestEntityFixture.EntityWithValidAnnotation("민준", 29);
                TestEntityFixture.EntityWithValidAnnotation entityThree =
                        new TestEntityFixture.EntityWithValidAnnotation("민준", 29);

                String insertDMLOne = insertQueryBuilder.prepareStatement(entityAttribute.createEntityContext(entityOne));
                String insertDMLTwo = insertQueryBuilder.prepareStatement(entityAttribute.createEntityContext(entityTwo));
                String insertDMLThree = insertQueryBuilder.prepareStatement(entityAttribute.createEntityContext(entityThree));

                Assertions.assertAll(
                        () -> Assertions.assertDoesNotThrow(() -> jdbcTemplate.execute(insertDMLOne)),
                        () -> Assertions.assertDoesNotThrow(() -> jdbcTemplate.execute(insertDMLTwo)),
                        () -> Assertions.assertDoesNotThrow(() -> jdbcTemplate.execute(insertDMLThree))
                );

                List<TestEntityFixture.EntityWithValidAnnotation> entities =
                        jdbcTemplate.queryForObject("SELECT * FROM ENTITY_NAME;", new TestEntityRowMapper());

                assertThat(entities.size()).isEqualTo(3);
            }
        }
    }
}
