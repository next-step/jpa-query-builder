package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.exception.NotEntityException;
import persistence.study.Car;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Query 의")
class QueryTest {

    @Nested
    @DisplayName("생성자는")
    class Constructor {

        @Test
        @DisplayName("@Entity가 없는 클래스일 경우, 예외를 던진다.")
        void fail_NotEntity() {
            //given
            Class<Car> notEntityClass = Car.class;

            //when & then
            assertThatThrownBy(() -> new Query(QueryType.CREATE, notEntityClass))
                    .isInstanceOf(NotEntityException.class);
        }
    }

    @Nested
    @DisplayName("getSql 메서드는")
    class GetSql {
        @Test
        @DisplayName("쿼리타입, 테이블 이름, 테이블 필드명을 가진 쿼리를 반환한다.")
        void sqlWithType_TableName_Fields() {
            //given
            Class<Person> targetClass = Person.class;
            QueryType type = QueryType.CREATE;
            String tableName = targetClass.getSimpleName();
            List<String> fieldNames = Arrays.stream(targetClass.getDeclaredFields())
                    .map(Field::getName)
                    .collect(Collectors.toList());

            //when

            String sql = new Query(type, targetClass).getSql();

            //then
            org.junit.jupiter.api.Assertions.assertAll(
                    () -> assertThat(sql).contains(type.name()),
                    () -> assertThat(sql).contains(tableName),
                    () -> assertThat(sql).contains(fieldNames)
            );
        }
    }
}
