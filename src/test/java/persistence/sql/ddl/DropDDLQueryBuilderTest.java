package persistence.sql.ddl;

import entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("DropDDLQueryBuilder 클래스의")
public class DropDDLQueryBuilderTest {
    @Nested
    @DisplayName("of 메소드는")
    class of {
        @Nested
        @DisplayName("유효한 엔티티 정보가 주어지면")
        class withValidEntity {
            @Test
            @DisplayName("DROP DDL을 리턴한다.")
            void testOf() {
                String ddl = DropDDLQueryBuilder.of(Person.class);
                assertThat(ddl).isEqualTo("DROP TABLE users;");
            }
        }
    }
}
