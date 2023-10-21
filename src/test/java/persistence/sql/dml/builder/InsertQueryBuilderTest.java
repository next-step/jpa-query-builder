package persistence.sql.dml.builder;

import entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.fixture.TestEntityFixture;
import persistence.sql.attribute.EntityAttribute;
import persistence.sql.parser.AttributeParser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("InsertQueryBuilder 클래스의")
public class InsertQueryBuilderTest {
    AttributeParser attributeParser = new AttributeParser();
    Person person = new Person(1L, "민준", 29, "민준.com");

    @Nested
    @DisplayName("prepareStatement 메소드는")
    class prepareStatement {
        @Nested
        @DisplayName("유효한 인자가 주어지면")
        class withValidArgs {
            @Test
            @DisplayName("적절한 statement를 반환한다.")
            void returnStatement() {
                EntityAttribute entityAttribute =
                        EntityAttribute.of(TestEntityFixture.EntityWithValidAnnotation.class, attributeParser);
                InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
                String statement = insertQueryBuilder.prepareStatement(entityAttribute.createEntityContext(person));
                assertThat(statement)
                        .isEqualTo("INSERT INTO entity_name( id, name, old ) VALUES ( '1', '민준', '29' )");
            }
        }
    }
}
