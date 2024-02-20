package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.sql.Person;
import persistence.exception.NotEntityException;
import persistence.study.Car;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CreateQueryBuilder 의")
class CreateQueryBuilderTest {

    @Nested
    @DisplayName("생성자는")
    class Constructor {

        @Test
        @DisplayName("@Entity가 없는 클래스일 경우, 예외를 던진다.")
        void fail_NotEntity() {
            //given
            Class<Car> notEntityClass = Car.class;

            //when & then
            assertThatThrownBy(() -> new CreateQueryBuilder(notEntityClass))
                    .isInstanceOf(NotEntityException.class);
        }
    }

    @Nested
    @DisplayName("build 메서드는")
    class Build {
        @Test
        @DisplayName("CREATE 쿼리를 반환한다.")
        void sqlForCreate() {
            //given
            Class<Person> targetClass = Person.class;

            //when
            String sql = new CreateQueryBuilder(targetClass).build();

            //then
            assertThat(sql).isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL)");
        }
    }
}
