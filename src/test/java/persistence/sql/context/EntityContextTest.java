package persistence.sql.context;

import entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.sql.attribute.GeneralAttribute;
import persistence.sql.attribute.StringTypeGeneralAttribute;
import persistence.sql.attribute.id.IdAttribute;
import persistence.sql.attribute.id.LongTypeIdAttribute;
import persistence.sql.parser.ValueParser;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("EntityContext 클래스의 ")
public class EntityContextTest {
    @Nested
    @DisplayName("of 메소드는")
    class prepareDML {
        Person person = new Person(1L, "민준", 29, "민준.com");

        @Test
        @DisplayName("컨택스트를 캐싱하는 객체를 반환한다.")
        void returnInsertDML() throws NoSuchFieldException, IllegalAccessException {
            String tableName = "users";
            IdAttribute idAttribute =
                    LongTypeIdAttribute.of(Person.class.getDeclaredField("id"));
            List<GeneralAttribute> generalAttributes = List.of(
                    StringTypeGeneralAttribute.of(Person.class.getDeclaredField("name")),
                    StringTypeGeneralAttribute.of(Person.class.getDeclaredField("age")),
                    StringTypeGeneralAttribute.of(Person.class.getDeclaredField("email")
                    ));

            EntityContext entityContext = EntityContext.of(
                    tableName,
                    idAttribute,
                    generalAttributes,
                    new ValueParser(),
                    person
            );

            Assertions.assertAll(
                    () -> assertThat(entityContext.getTableName()),
                    () -> assertThat(entityContext.getIdAttributeWithValuePair().getKey()),
                    () -> assertThat(entityContext.getIdAttributeWithValuePair().getValue()),
                    () -> assertThat(entityContext.getAttributeWithValueMap().size()).isEqualTo(3)
            );
        }
    }
}
