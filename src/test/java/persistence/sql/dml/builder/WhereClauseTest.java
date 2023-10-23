package persistence.sql.dml.builder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("WhereClause 클래스의")
public class WhereClauseTest {
    @Nested
    @DisplayName("and 메소드는")
    public class and {
        @Nested
        @DisplayName("기존 조건이 없는 상태에서, 필드이름과 벨류가 주어지면")
        public class inEmptyCondition {

            @Test
            @DisplayName("조건을 새로 생성한다.")
            void addCondition() {
                WhereClause whereClause = new WhereClause();
                whereClause.and("test", "test value");
                assertThat(whereClause.toString()).isEqualTo(" WHERE test = 'test value'");
            }
        }

        @Nested
        @DisplayName("기존 조건이 있는 상태에서, 필드이름과 벨류가 주어지면")
        public class inCondition {

            @Test
            @DisplayName("조건을 이어붙인다.")
            void addCondition() {
                WhereClause whereClause = new WhereClause();
                whereClause.and("test", "test value");
                whereClause.and("민준", "1");

                assertThat(whereClause.toString()).isEqualTo(" WHERE test = 'test value' AND 민준 = '1'");
            }
        }
    }
}
