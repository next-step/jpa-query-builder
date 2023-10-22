package persistence.sql.ddl.builder;

import entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.DatabaseTest;
import persistence.entitiy.attribute.EntityAttribute;
import persistence.sql.ddl.converter.SqlConverter;
import persistence.sql.infra.H2SqlConverter;
import persistence.sql.parser.AttributeParser;

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
                EntityAttribute entityAttribute = EntityAttribute.of(Person.class, parser);

                String dropDDL = DDLQueryBuilderFactory.createQueryBuilder(DROP)
                        .prepareStatement(entityAttribute, sqlConverter);

                String message = Assertions.assertThrows(RuntimeException.class, () -> jdbcTemplate.execute(dropDDL))
                        .getMessage();

                assertThat(message).contains("Table \"USERS\" not found; SQL statement");
                assertThat(dropDDL).isEqualTo("DROP TABLE users;");
            }
        }
    }
}
