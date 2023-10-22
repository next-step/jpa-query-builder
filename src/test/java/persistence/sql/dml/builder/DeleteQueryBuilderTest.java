package persistence.sql.dml.builder;

import entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("DeleteQueryBuilder 클래스의")
public class DeleteQueryBuilderTest {
    @Nested
    @DisplayName("prepareStatement 클래스의")
    public class prepareStatement {
        @Nested
        @DisplayName("클래스정보와 이이디값이 주어지면")
        public class withValidArgs {
            @Test
            @DisplayName("적절한 DML을 반환한다.")
            void returnDML() {
                DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
                String dml = deleteQueryBuilder.prepareStatement(Person.class, String.valueOf(1));
                assertThat(dml).isEqualTo("DELETE * FROM Person where id = 1");
            }
        }
    }
}
