package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.exception.NotEntityException;
import persistence.study.Car;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Query 의")
class DdlQueryTest {

    @Nested
    @DisplayName("생성자는")
    class Constructor {

        @Test
        @DisplayName("@Entity가 없는 클래스일 경우, 예외를 던진다.")
        void fail_NotEntity() {
            //given
            Class<Car> notEntityClass = Car.class;

            //when & then
            assertThatThrownBy(() -> new DdlQuery(QueryType.CREATE, notEntityClass))
                    .isInstanceOf(NotEntityException.class);
        }
    }

    @Nested
    @DisplayName("getSql 메서드는")
    class GetSql {
        @Test
        @DisplayName("CREATE 쿼리를 반환한다.")
        void sqlForCreate() {
            //given
            Class<Person> targetClass = Person.class;
            QueryType type = QueryType.CREATE;

            //when
            String sql = new DdlQuery(type, targetClass).getSql();

            //then
            assertThat(sql).contains(type.name());
        }

        @Test
        @DisplayName("Drop 쿼리를 반환한다.")
        void sqlForDrop() {
            //given
            Class<Person> targetClass = Person.class;
            QueryType type = QueryType.DROP;

            //when
            String sql = new DdlQuery(type, targetClass).getSql();

            //then
            assertThat(sql).contains(type.name());

        }
    }
}
