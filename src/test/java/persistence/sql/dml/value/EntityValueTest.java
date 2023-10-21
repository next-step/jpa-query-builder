package persistence.sql.dml.value;

import entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.attribute.EntityAttribute;
import persistence.sql.ddl.converter.SqlConverter;
import persistence.sql.ddl.parser.AttributeParser;
import persistence.sql.dml.wrapper.DMLWrapper;
import persistence.sql.dml.wrapper.InsertDMLWrapper;
import persistence.sql.infra.H2SqlConverter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("EntityValue 클래스의 ")
public class EntityValueTest {
    @Nested
    @DisplayName("prepareDML 메소드는")
    class prepareDML {
        @Test
        @DisplayName("INSERT DML을 반환한다.")
        void returnInsertDML() {
            SqlConverter sqlConverter = new H2SqlConverter();
            AttributeParser parser = new AttributeParser(sqlConverter);
            EntityAttribute entityAttribute = EntityAttribute.of(Person.class, parser);
            Person person = new Person(1L, "민준", 29, "민준.com");

            EntityValue entityValue = entityAttribute.makeEntityValue(person);
            DMLWrapper dmlWrapper = new InsertDMLWrapper();
            String statement = entityValue.prepareDML(dmlWrapper);

            assertThat(statement)
                    .isEqualTo("INSERT INTO users( id, nick_name, old, email ) VALUES ( 1 민준, 29, 민준.com )");

        }
    }
}
