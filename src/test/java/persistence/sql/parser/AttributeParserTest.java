package persistence.sql.parser;

import entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("AttributeParser 클래스의")
public class AttributeParserTest {
    @Nested
    @DisplayName("parseIdAttribute 메소드는")
    public class parseIdAttribute {
        @Nested
        @DisplayName("적절한 클래스타입이 주어지면")
        public class withValidArgs {

            @Test
            @DisplayName("IdAttribute를 반환한다.")
            void returnIdAttribute() {
                AttributeParser attributeParser = new AttributeParser();
                assertThat(attributeParser.parseIdAttribute(Person.class).getColumnName()).isEqualTo("id");
            }
        }
    }

    @Nested
    @DisplayName("parseGeneralAttributes 메소드는")
    public class parseGeneralAttributes {
        @Nested
        @DisplayName("적절한 클래스타입이 주어지면")
        public class withValidArgs {

            @Test
            @DisplayName("IdAttribute를 반환한다.")
            void returnIdAttribute() {
                AttributeParser attributeParser = new AttributeParser();
                Assertions.assertAll(
                        () -> assertThat(attributeParser.parseGeneralAttributes(Person.class).get(0).getFieldName()).isEqualTo("name"),
                        () -> assertThat(attributeParser.parseGeneralAttributes(Person.class).get(1).getFieldName()).isEqualTo("age"),
                        () -> assertThat(attributeParser.parseGeneralAttributes(Person.class).get(2).getFieldName()).isEqualTo("email"),
                        () -> assertThat(attributeParser.parseGeneralAttributes(Person.class).get(0).getColumnName()).isEqualTo("nick_name"),
                        () -> assertThat(attributeParser.parseGeneralAttributes(Person.class).get(1).getColumnName()).isEqualTo("old"),
                        () -> assertThat(attributeParser.parseGeneralAttributes(Person.class).get(2).getColumnName()).isEqualTo("email")
                );
            }
        }
    }
}
