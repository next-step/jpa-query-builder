package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.exception.NotEntityException;
import persistence.sql.Person;
import persistence.sql.domain.dialect.Dialect;
import persistence.sql.domain.dialect.H2Dialect;
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
            CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(new H2Dialect());

            //when & then
            assertThatThrownBy(() -> createQueryBuilder.build(notEntityClass))
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
            Class<Person> target = Person.class;

            //when
            CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(new H2Dialect());
            String query = createQueryBuilder.build(target);

            //then
            assertThat(query).isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR, old INTEGER, email VARCHAR NOT NULL)");
        }
    }
}
