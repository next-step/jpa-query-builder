package persistence.sql.dml.builder;

import entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.entity.attribute.AttributeParser;
import persistence.entity.attribute.EntityAttribute;
import persistence.fixture.TestEntityFixture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("InsertQueryBuilder 클래스의")
public class InsertQueryBuilderTest {
    AttributeParser attributeParser = new AttributeParser();


    @Nested
    @DisplayName("prepareStatement 메소드는")
    class prepareStatement {
        @Nested
        @DisplayName("유효한 인자가 주어지면")
        class withValidArgs {
            @Test
            @DisplayName("적절한 statement를 반환한다.")
            void returnStatement() {
                Person person = new Person(1L, "민준", 29, "민준.com");
                EntityAttribute entityAttribute =
                        EntityAttribute.of(TestEntityFixture.SampleOneWithValidAnnotation.class, attributeParser);
                InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
                String statement = insertQueryBuilder.prepareStatement(entityAttribute, person);
                assertThat(statement)
                        .isEqualTo("INSERT INTO entity_name( id, name, old ) VALUES ( '1', '민준', '29' )");
            }
        }
    }
}
