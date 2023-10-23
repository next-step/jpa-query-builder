package persistence.sql.ddl.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.DatabaseTest;
import persistence.entity.attribute.AttributeParser;
import persistence.entity.attribute.EntityAttribute;
import persistence.fixture.TestEntityFixture;
import persistence.sql.ddl.converter.SqlConverter;
import persistence.sql.infra.H2SqlConverter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static persistence.sql.common.DDLType.DROP;

@Nested
@DisplayName("DropDDLQueryBuilder 클래스의")
public class DropDDLQueryBuilderTest extends DatabaseTest {
    private final SqlConverter sqlConverter = new H2SqlConverter();
    private final AttributeParser parser = new AttributeParser();

    @Nested
    @DisplayName("prepareStatement 메소드는")
    class prepareStatement {
        @Nested
        @DisplayName("유효한 엔티티 정보가 주어지면")
        class withValidEntity {
            @Test
            @DisplayName("DROP DDL을 리턴한다.")
            void returnDDL() {
                EntityAttribute entityAttribute = EntityAttribute.of(TestEntityFixture.SampleTwoWithValidAnnotation.class, parser);

                String dropDDL = DDLQueryBuilderFactory.createQueryBuilder(DROP)
                        .prepareStatement(entityAttribute, sqlConverter);

                assertThat(dropDDL).isEqualTo("DROP TABLE two;");
            }
        }
    }
}
